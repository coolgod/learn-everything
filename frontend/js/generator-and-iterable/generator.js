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

// iterate through each yielded value in a generator
for (let yieldValue of fooGenerator()) {
    console.log(yieldValue);
}

// generator also conforms to iterator protocol
const foo = fooGenerator();
while (true) {
    const { value, done } = foo.next();
    if (done) {
        break;
    }
    console.log(value);
}

function* fooGenerator() {
    const a = 2;
    const b = 3;
    yield 1;
    console.log('after yield 1');
    yield a;
    console.log('after yield ' +a);
    yield b;
    console.log('after yield ' + b);
}