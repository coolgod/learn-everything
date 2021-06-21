const childProcess = require('child_process');
const net = require('net');

const child1 = childProcess.fork('child.js');
const child2 = childProcess.fork('child.js');

const server = net.createServer();
server.listen(9000, () => {
    // the underlying file descriptor (server socket) and message are sent to child processes through IPC
    // in each child process a new server object is *recreated* using the same *file descriptor*
    // thus they can listen on the same port
    child1.send('server', server);
    child2.send('server', server);

    // close the server
    // so that parent will not receive new connections
    server.close();
});
