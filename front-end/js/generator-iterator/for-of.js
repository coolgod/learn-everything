// iterate through each element of array
const arr = [1, 2, 3];

for (let ele of arr) {
    console.log(ele);
}

// iterate through each charactor of a string
const str = 'abc';

for (let char of str) {
    console.log(char);
}

// iterate through each yieled value in a generator
for (let yieldValue of fooGenerator()) {
    console.log(yieldValue);
}

function* fooGenerator() {
    const a = 2;
    const b = 3;
    yield 1;
    console.log('_1');
    yield a;
    console.log('_' +a);
    yield b;
    console.log('_' + b);
}