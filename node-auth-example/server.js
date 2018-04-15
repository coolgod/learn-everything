
// import modules
var express  = require('express');
var mongoose = require('mongoose');
var passport = require('passport');
var flash    = require('connect-flash');
var morgan       = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser   = require('body-parser');
var session      = require('express-session');

var configPassport = require('./config/passport');
var routes = require('./app/routes');

// db
var dbUrl = 'mongodb://coolgod:123456@localhost:27017/node-auth-example';
mongoose.connect(dbUrl, { useMongoClient: true });

// create server
var app      = express();
var port     = process.env.PORT || 8080;

configPassport(passport);

// middlewares
app.use(morgan('dev'));
app.use(cookieParser());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// template engine
app.set('view engine', 'ejs');

// required for passport
app.use(session({ 
    resave: false, // TODO: figure out meaning of resave and saveUninitialized
    secret: 'coolgod',
    saveUninitialized: true
})); // for ecryption
app.use(passport.initialize());
app.use(passport.session());
app.use(flash()); // flash message is the message stored in session and cleared after displayed to user?

// routing
routes(app, passport);

// lauch server
app.listen(port);
console.log('server started on port %j', port);