// iterable is a protocol (interface)

// define MyIterable
function MyIterable() {
    this.internalArr = [...arguments];
}

MyIterable.prototype.push = function(ele) {
    this.internalArr.push(ele);
}

MyIterable.prototype.pop = function() {
    this.internalArr.pop();
}

MyIterable.prototype[Symbol.toStringTag] = 'MyIterable';

// A GeneratorFunction is a special type of function that works as a factory for iterators. 
// it returns an generator object
// this generator object has next() method
// so it's also an iterator
MyIterable.prototype[Symbol.iterator] = function* () {
    let index = 0;
    while (index < this.internalArr.length) {
        yield this.internalArr[index];
        index += 1;
    }
}

MyIterable.prototype[Symbol.toPrimitive] = function() {
    return this.internalArr.join(' | ');
}

// test
console.log(typeof MyIterable === 'function');

const myIterable = new MyIterable(1, 2, 3);
console.log(myIterable.toString()); // [object MyIterable]
console.log(myIterable); // MyIterable { internalArr: [ 1, 2, 3 ] }
console.log(String(myIterable)); // 1 | 2 | 3

for (let ele of myIterable) {
    console.log(ele);
}
