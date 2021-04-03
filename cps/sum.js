const {
	trampoline,
	trampoline2
} = require('./trampoline');

// no CPS
const sum = x => x == 0 ? 0 : x + sum(x - 1);
console.log(sum(5));

// CPS, no trampoline
const sum_cps = (x, kont) => x == 0 ? kont(0) : sum_cps(x - 1, z => kont(x + z));
console.log(sum_cps(5, res => res));

// CPS + trampoline
const sum_trampoline = (x, kont) => {
	if (x == 0) {
		return {
			v: 0,
			kont: kont
		};
	}

	return {
		v: null,
		kont: r => sum_trampoline(
			x - 1,
			z => ({
				v: z + x,
				kont: kont
			})
		)
	}
}

console.log(trampoline(sum_trampoline(5, res => ({
	v: res,
	kont: null
}))));

// CPS + trampoline w/ bind
const sum_trampoline2 = (x, kont) => {
	if (x == 0) {
		return kont.bind(null, 0);
	}

	return sum_trampoline2.bind(
		null,
		x - 1,
		res => kont.bind(null, res + x)
	);
}
console.log(trampoline2(sum_trampoline2(5, res => res)));

// factorial is almost the same