function add() {
    // 第一次执行，初始化_args存储后续每次调用传入的参数
    const _args = [].slice.call(arguments);

    function _add() {
        const _newArgs = [].slice.call(arguments);
        _args.push(..._newArgs);
        return _add;
    }
    
    _add.valueOf = function() {
        return _args.reduce((sum, curr) => (sum + curr), 0);
    }

    return _add;
};

console.log(add(1));
console.log(add(1) + 0);
// console.log(add(1)(2).toString());
// console.log(add(1)(2)(3).toString());
// console.log(add(1)(2)(3, 4, 5).toString());
console.log(add(1, 2)(3)(4, 5) + 0);
console.log(add(1, 2)(3)(4, 5) === 15);
// console.log(add(1, 2, 3, 4, 5).toString());

