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
    });

    cluster.on('exit', function(worker, code, signal) {
        console.log(`Worker ${worker.process.pid} died with code: ${code} and signal: ${signal}`);
        console.log('Starting a new worker');
        cluster.fork();
    });
} else {
    // 过3秒随机自杀，30%概率
    // Master 会尝试重启
    if (Math.random() < 0.3) {
        setTimeout(function() {
            console.log(`${process.pid} is commiting suicide now`);
            process.kill(process.pid);
        }, 3000);
    }
}