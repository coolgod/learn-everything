# 基于nginx的image的最新（latest）版本
FROM nginx:latest

# 把文件拷贝到制作好的image里去
COPY index.html /usr/share/nginx/html
COPY linux.png /usr/share/nginx/html

# 暴露80，443端口 -> http/https默认端口
EXPOSE 80 443 	

# docker启动container时默认执行的命令和参数
CMD ["nginx", "-g", "daemon off;"]
