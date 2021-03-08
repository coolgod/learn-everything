function factorial(x, kont) {
    if (x == 1) {
        kont(1);
        return;
    }
    factorial(x - 1, res => kont(res * x))
}

factorial(5, console.log);