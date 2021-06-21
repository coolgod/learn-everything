const childProcess = require('child_process');
const net = require('net');
const os = require('os');

const tcpServer = net.createServer();
tcpServer.listen(9000);

const workers = {};

for (let i = 0; i < os.cpus().length; i += 1) {
    createWorker();
}

process.on('exit', () => {
    for (let pid in workers) {
        workers[pid].kill();
    }
});

function createWorker() {
    const worker = childProcess.fork('child.js');
    
    worker.on('message', (m) => {
        if (m.act === 'suicide') {
            createWorker();
        }
    });

    worker.on('exit', () => {
        console.log(worker.pid, 'worker exited');
        delete workers[worker.pid];
    });

    worker.send('server', tcpServer);
    workers[worker.pid] = worker;
    console.log(worker.pid, 'worker created');
}