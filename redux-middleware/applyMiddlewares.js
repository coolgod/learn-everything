function applyMiddlewares(middlewares) {
    return function(store) {
        middlewares = middlewares.slice();
        middlewares.reverse();
        let dispatch = store.dispatch;
        middlewares.forEach(function(middleware) {
            // iterating and updating dispatch
            dispatch = middleware(store)(dispatch);
        });
        // return a copy of the store with the updated dispatch
        return Object.assign({}, store, { dispatch })
    }
}

module.exports = applyMiddlewares;
