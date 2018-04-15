tutorial code implemented following tutorials here:

https://scotch.io/tutorials/easy-node-authentication-setup-and-local

# set up database
```
# in shell
$ mongo # launch mongodb shell

# in mongodb shell
> use auth-node-example # switch to a new db
> db.createUser( # authorization
    {
        user: 'coolgod',
        pwd: '123456',
        roles: ['readWrite']
    }
);
```