function logger(store) {
    return function(dispatch) {
        return function(action) {
            console.log('[logger]', 'before dispatch', action);
            console.log('[logger]', 'state', store.getState());
            const result = dispatch(action);
            console.log('[logger]', 'after dispatch', action);
            console.log('[logger]', 'state', store.getState());
            return result;
        }
    }
}

module.exports = logger;
