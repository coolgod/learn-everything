// factorial
function f(x, kont) {
    if (x == 1) {
        kont(1);
        return;
    }
    f(x - 1, res => kont(res * x))
}

// f(5) = f(4, res => kont(res * 5))
//      = f(3, res => (res => kont(res * 5))(res * 4))
//      = f(2, res => (res => (res => kont(res * 5))(res * 4))(res * 3))
//      = f(1, res => (res => (res => (res => kont(res * 5))(res * 4))(res * 3))(res * 2))
//      = (res => (res => (res => (res => kont(res * 5))(res * 4))(res * 3))(res * 2))(1)
f(5, console.log);