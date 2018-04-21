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
        // 主线程的event loop callback queue
        console.log(`Worker ${worker.process.pid} is online`);
    });

    cluster.on('exit', function(worker, code, signal) {
        console.log(`Worker ${worker.process.pid} died with code: ${code} and signal: ${signal}`);
        console.log('Starting a new worker');
        cluster.fork();
    });
} else {
    console.log(cluster.workers); // 打印undefined，worker进程里并没有这个对象

    console.log(`Worker ${process.pid} starting http server now...`);
    http.createServer(function(req, res) {
        res.writeHead(200);
        res.end('Hello world\n');
    })
    .listen(8000);
}