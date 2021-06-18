const listeners = new WeakMap();

function on(object, event, handler) {
    if (!listeners.has(object)) {
        listeners.set(object, {});
    }

    const objectListeners = listeners.get(object);
    if (!objectListeners[event]) {
        objectListeners[event] = [];
    }

    objectListeners[event].push(handler);
}

function emit(object, event) {
    const objectListeners = listeners.get(object);
    if (objectListeners && objectListeners[event]) {
        objectListeners[event].forEach((handler) => handler.call(object, event));
    }
}

let obj = { test: 'hello' };

on(obj, 'hello', function(...args) {
    console.log(args);
});

emit(obj, 'hello');