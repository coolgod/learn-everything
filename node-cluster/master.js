const cluster = require('cluster');

console.log(`started master with ${process.pid}`);

cluster.fork();

// 不中断重启worker
process.on('SIGHUP', function() {
    console.log('Reloading...');
    // 新起worker进程
    const newWorker = cluster.fork();
    // 关闭以前worker进程
    newWorker.once('listening', function() {
        for(let id in cluster.workers) {
            if (id === newWorker.id.toString()) continue;
            cluster.workers[id].kill('SIGTERM');
        }
    });
});