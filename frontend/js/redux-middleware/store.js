const EventEmitter = require('events');

function createStore(applyMiddlewares) {
    // for each store
    // create an event emitter to simulate actions passing to reducers
    const eventEmitter = new EventEmitter();
    eventEmitter.on('dispatch',  function(action){
        console.log('action %j passed to reducers', action);
    });
    // create a store object
    const store = {
        dispatch: function(action) {
            eventEmitter.emit('dispatch', action);
        },
        getState: function() {
            return {'storeState': 'testState'};
        }
    };
    // apply middlewares
    if (applyMiddlewares) {
        if (typeof applyMiddlewares === 'function') {
            return applyMiddlewares(store);
        }
        throw new TypeError('applyMiddleware has to be a function');
    }
    return store;
}

module.exports = createStore;
