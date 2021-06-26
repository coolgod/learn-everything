function newObject(constructor, ...args) {
    const that = {};

    that.__proto__ = constructor.prototype;

    const ret = constructor.call(that, ...args);
    return typeof ret === 'object' ? ret : that;
}

function Test(name) {
    this.name = name;
    this.hello = function() {
        console.log(this.name);
    }
}

Test.prototype.hello2 = function() {
    console.log('hello2');
}

const t = newObject(Test, 'test');
t.hello();
t.hello2();