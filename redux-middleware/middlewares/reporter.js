function reporter(store) {
    return function(dispatch) {
        return function(action) {
            try {
                if (action === 'test error') {
                    throw new Error('test error');
                } else {
                    return dispatch(action);
                }
            } catch (err) {
                console.error('[reporter]', 'caught err', err);
                console.log('[reporter]', 'state', store.getState());
                throw err;
            }
        }
    }
}

module.exports = reporter;
