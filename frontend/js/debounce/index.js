const debounce = require('./debounce');

const debouncedTest = debounce(test, 1000);

// grouped into one call
debouncedTest();
debouncedTest();
debouncedTest();

// grouped into one call
setTimeout(debouncedTest, 2000);
setTimeout(debouncedTest, 2100);
setTimeout(debouncedTest, 2200);

// one call
setTimeout(debouncedTest, 4000);

function test() {
    console.log('test ' + new Date().toUTCString());
}