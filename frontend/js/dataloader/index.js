const DataLoader = require('dataloader');

const testLoader = new DataLoader(testBatchFunc);

testLoader.load();

function testBatchFunc(keys) {

}