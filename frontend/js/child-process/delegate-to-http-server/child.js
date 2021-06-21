const http = require('http');
const httpServer = http.createServer((req, res) => {
    res.writeHead(200, { 'Content-Type': 'text/plain' });
    res.end(`response from child ${process.pid}\n`);
});

process.on('message', (m, tcpServer) => {
    console.log(process.pid, `receving message "${m}"`);
    if (m === 'server') {
        tcpServer.on('connection', socket => {
            httpServer.emit('connection', socket);
        });
    }
});