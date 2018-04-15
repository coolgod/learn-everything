var LocalStrategy = require('passport-local').Strategy;

var User = require('../app/models/user');

module.exports = function(passport) {

    // serialize user for the session
    passport.serializeUser(function(user, done) {
        done(null, user.id);
    });

    // deserialize user
    passport.deserializeUser(function(id, done) {
        User.findById(id, function(err, user) {
            done(err, user);
        });
    });

    passport.use('local-signup', new LocalStrategy({
            usernameField: 'email',
            passwordField: 'password',
            passReqToCallback: true
        },
        function(req, email, password, done) {
            User.findOne({ 'local.email': email }, function(err, user) {
                if (err) return done(err);

                if (user) {
                    return done(null, false, req.flash('signupMsg', 'The email has been taken'));
                } else {
                    var newUser = new User();
                    newUser.local.email = email;
                    newUser.local.password = newUser.generateHash(password);

                    newUser.save(function(err) {
                        if(err) throw err;
                        return done(null, newUser);
                    });
                }
            });
        }
    ));

    passport.use('local-login', new LocalStrategy({
        usernameField : 'email',
        passwordField : 'password',
        passReqToCallback : true
    },
    function(req, email, password, done) {
        console.log('local-login');
        User.findOne({ 'local.email' :  email }, function(err, user) {
            if (err) return done(err);

            if (!user)
                return done(null, false, req.flash('loginMsg', 'No user found.'));

            if (!user.validPassword(password))
                return done(null, false, req.flash('loginMsg', 'Oops! Wrong password.'));

            return done(null, user);
        });

    }));
};