const fs = require('fs');
const path = require('path');

async function readFile(relativePath) {
    const absolutePath = path.join(__dirname, relativePath);

    return new Promise((resolve, reject) => {
        fs.readFile(absolutePath, { encoding: 'utf-8' }, (err, data) => {
            if (err) {
                reject(err);
            } else {
                resolve(data);
            }
        });
    });
}

readFile('./test.txt').then((data) => console.log(data));