const http = require('http');

const CORS_HEADERS = {
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Methods": "OPTIONS, POST, GET",
    'Cache-Control': 'no-cache',
    'Content-Type': 'text/event-stream'
};

const server = http.createServer((req, res) => {
    res.writeHead(200, CORS_HEADERS);
    setInterval(() => {
        const id = Date.now();
        res.write(`id: ${id}\n`);
        res.write('event: message\n');
        res.write('retry: 100000\n');
        res.write(`data: hello ${id}\n\n`);
    }, 5000)
    // do not close the socket
    // res.end();
});

server.listen(9000, () => {
    console.log('listening to port 9000');
});