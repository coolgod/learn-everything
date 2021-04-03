const trampoline = (kont_v) => {
	while (kont_v.kont) {
		kont_v = kont_v.kont(kont_v.v);
	}
	return kont_v.v;
}

const trampoline2 = (kont) => {
	while (typeof kont === 'function') {
		kont = kont();
	}
	return kont;
}

module.exports.trampoline = trampoline;
module.exports.trampoline2 = trampoline2;