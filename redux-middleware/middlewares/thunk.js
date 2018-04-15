function thunk(store) {
    return function(dispatch) {
        return function(action) {
            if (typeof action === 'function') {
                return action(dispatch);
            } else {
                return dispatch(action);
            }
        }
    }
}

module.exports = thunk;
