const cluster = require('cluster');

if (cluster.isMaster) {
    require('./master');
    return;
}

const express = require('express');
const http = require('http');
const app = express();

app.get('/', function(req, res) {
    res.send('hello world');
});

http.createServer(app)
.listen(9000, function() {
    console.log('http://localhost:9000')
});