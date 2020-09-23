const createStore = require('./store');
const applyMiddlewares = require('./applyMiddlewares');
const logger = require('./middlewares/logger');
const reporter = require('./middlewares/reporter');
const thunk = require('./middlewares/thunk');

const store = createStore(applyMiddlewares(
    [thunk, logger, reporter]
));

console.log('----------------');
store.dispatch('test action 1');
// console.log('----------------');
// store.dispatch('test error');
console.log('----------------');
store.dispatch(asyncLog());

function asyncLog() {
    return function(dispatch) {
        setTimeout((function() {
            dispatch('test thunk dispatch after 2 seconds');
            dispatch('test error'); // after 2 seconds throw err
        }), 2000);
    }
}
