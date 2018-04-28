// self = this = global context of worker
importScripts('http://127.0.0.1:8887/external.js');

self.addEventListener('message', function(e) {
    console.log('Received message: ', e.data);
    const data = e.data;
    switch(data.cmd) {
        case 'start':
            self.postMessage('WORKER STARTED: ' + data.msg);
            break;
        case 'stop':
            self.postMessage('WORKER STOPPED: ' + data.msg);
            break;
        default:
            self.postMessage('Unknown command: ' + data.msg);
    }
}, false);