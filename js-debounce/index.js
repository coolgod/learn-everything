const debounce = require('./debounce');

const debouncedTest = debounce(test, 1000);

debouncedTest();
debouncedTest();
debouncedTest();

setTimeout(debouncedTest, 2000);
setTimeout(debouncedTest, 2100);
setTimeout(debouncedTest, 2200);

setTimeout(debouncedTest, 4000);

function test() {
    console.log('test ' + new Date().toUTCString());
}