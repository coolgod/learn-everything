const fs = require('fs');
const path = require('path');

function promisify(func) {
    function asyncFunc(...args) {
        return new Promise((resolve, reject) => {
            func(...args, (err, data) => {
                if (err) {
                    reject(err);
                } else {
                    resolve(data);
                }
            });
        });
    }
    return asyncFunc;
}

const readFileAsync = promisify(fs.readFile);

readFileAsync(path.join(__dirname, './test.txt'), { encoding: 'utf-8' })
    .then((data) => console.log(data));