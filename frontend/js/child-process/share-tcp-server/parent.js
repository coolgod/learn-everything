const childProcess = require('child_process');
const net = require('net');

const child1 = childProcess.fork('child.js');
const child2 = childProcess.fork('child.js');

// a new connetion may be handled by
// parent, child1 or child2
const server = net.createServer();
server.on('connection', (socket) => {
    console.log(process.pid, 'connection established');
    socket.end(`response from parent ${process.pid}\n`);
});
server.listen(9000, () => {
    child1.send('server', server);
    child2.send('server', server);
});
