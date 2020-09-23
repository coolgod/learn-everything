function test() {
    console.log([].slice.call(arguments)); // arguments as this when executing slice()
}

test(1, 2, 3); // [1, 2, 3]