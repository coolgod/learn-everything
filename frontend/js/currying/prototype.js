function test1() {
}

test1.toString = function() {
    return 'test1';
}

function test2() {
}

test2.prototype.toString = function() {
    return 'test2';
}

console.log(test1); // { [Function: test1] toString: [Function] }
console.log(test1 + ''); // test1
console.log(test1 instanceof Function); // true
console.log(test2); // [Function: test2]

const test2Instance = new test2();
console.log(test2Instance); // test2 {}
console.log(test2Instance.toString()); // test2
console.log(test1 instanceof Function); // true