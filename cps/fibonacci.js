const {
    trampoline,
    trampoline2
} = require('./trampoline');

// no CPS
const fib = x => {
    if (x === 0) {
        return 0
    }

    if (x === 1) {
        return 1;
    }

    return fib(x - 1) + fib(x - 2);
}
console.log(fib(5));

// CPS
const fib_cps = (x, kont) => {
    if (x == 0 || x == 1) {
        return kont(x);
    }
    return fib_cps(x - 2, z2 => fib_cps(x - 1, z1 => kont(z1 + z2)));
}
console.log(fib_cps(5, res => res));

// CPS + trampoline
const fib_trampoline = (x, kont) => {
    if (x == 0 || x == 1) {
        return {
            v: x,
            kont: kont
        };
    }

    // WTH...
    return {
        v: null,
        kont: r1 => fib_trampoline(
            x - 2,
            z2 => ({
                v: null,
                kont: r2 => fib_trampoline(
                    x - 1,
                    z1 => ({
                        v: z1 + z2,
                        kont: kont,
                    })
                )
            })
        )
    }
}
console.log(trampoline(
    fib_cps(
        5,
        res => ({
            v: res,
            kont: null
        })
    )
));

// CPS + trampoline w/ bind
const fib_trampoline2 = (x, kont) => {
    if (x == 0 || x == 1) {
        return kont.bind(null, x);
    }

    // WTH...
    return fib_trampoline2.bind(
        null,
        x - 2,
        z2 => fib_trampoline2.bind(
            null,
            x - 1,
            z1 => kont.bind(null, z1 + z2)
        )
    );
}
console.log(trampoline2(
    fib_cps(
        5,
        res => res
    )
));
