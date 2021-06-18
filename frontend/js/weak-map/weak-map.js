// Map holds strong reference to keys
let obj1 = {};
const map = new Map();
map.set(obj1, 1);
obj1 = null;
console.log(map.entries());

// WeakMap holds weak reference to keys
let obj2 = {};
const wm = new WeakMap();
wm.set(obj2, 1);
obj2 = null; // obj2 may be recycled