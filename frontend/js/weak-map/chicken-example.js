function Chicken() {
    this.age = Math.random()*20;
};

function getChickenWeight(chicken) {
    return chicken.age * Math.random();
}

const chickens = new Array(5).map(() => new Chicken());

const weights = new WeakMap();
// 1. cannot iterate wm
// 2. if chickens references are cleared, wm will be recycled
// 核心思想：在不改变对象本身的情况下扩展对象
chickens.forEach(chicken => wm.set(chicken, getChickenWeight(chicken)));