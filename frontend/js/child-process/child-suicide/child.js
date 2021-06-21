const http = require('http');
const httpServer = http.createServer((req, res) => {
    res.writeHead(200, { 'Content-Type': 'text/plain' });
    res.end(`response from child ${process.pid}\n`);
    throw new Error('test suicide');
});

let worker;
process.on('message', (m, tcpServer) => {
    console.log(process.pid, `receving message "${m}"`);
    if (m === 'server') {
        worker = tcpServer;
        tcpServer.on('connection', socket => {
            httpServer.emit('connection', socket);
        });
    }
});

process.on('uncaughtException', (err) => {
    process.send({ act: 'suicide' });

    worker.close(() => process.exit(1));

    // force quite after 5 seconds
    setTimeout(() => process.exit(1), 5000);
});