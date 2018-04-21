const cluster = require('cluster');
const os = require('os');
const http = require('http');

const CPU_NUMBER = os.cpus().length;

if (cluster.isMaster) {
    for (let i = 0;  i < CPU_NUMBER; i += 1) {
        const worker = cluster.fork();
        console.log(`Master setting up worker ${i} now...`); 
    }
    cluster.on('online', function(worker) {
        console.log(`Worker ${worker.process.pid} is online`);

        // worker启动后，过5秒向worker发消息
        setTimeout(function() {
            worker.send('hello from master');
        }, 5000);
    });
} else {
    // 接收来自主进程的消息
    process.on('message', function(message) {
        console.log(`worker ${process.pid} receved msg from other process ${message}`);
    });
}