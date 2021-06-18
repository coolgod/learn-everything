const debounce = require('./debounce');

const debouncedTest = debounce(() => test('trailing'), 1000);
const debouncedTest2 = debounce(() => test('leading'), 1000, { leading: true });

// grouped into one call
console.log('3 calls sent at: ' + new Date().toUTCString());
debouncedTest();
debouncedTest();
debouncedTest();

// grouped into one call
console.log('3 calls scheduled at: ' + new Date().toUTCString());
setTimeout(debouncedTest, 2000);
setTimeout(debouncedTest, 2100);
setTimeout(debouncedTest, 2200);

// one call
console.log('1 call sent at: ' + new Date().toUTCString());
setTimeout(debouncedTest, 4000);

console.log('3 leading calls sent at: ' + new Date().toUTCString());
debouncedTest2();
debouncedTest2();
debouncedTest2();
setTimeout(debouncedTest2, 2000);

function test(content) {
    console.log('test ' + content + ' ' + new Date().toUTCString());
}