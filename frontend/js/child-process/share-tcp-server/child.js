process.on('message', (m, server) => {
    console.log(process.pid, `receving message "${m}"`);
    if (m === 'server') {
        server.on('connection', socket => {
            console.log(process.pid, 'connection established');
            socket.end(`response from child ${process.pid}\n`);
        });
    }
});