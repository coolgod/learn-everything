Xia: 从Docker官方教学repo克隆而来
# Linux Tweet App

This is very simple NGINX website that allows a user to send a tweet. 

It's mostly used as a sample application for Docker 101 workshops. 

To use it:

Build it:（存在了docker本地仓库，类似npm的全局仓库?)

`docker build -t linux_tweet_app .`

Find it:

`docker iamges`

Run it:

`docker container run --detach -p 9000:80 linux_tweet_app`

- 官方教程里把80转到80
- 如果你本机开了chrome就傻逼了
- 可以把9000转到80，然后访问`localhost:9000`
