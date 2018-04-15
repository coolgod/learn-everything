REQ-REP Socket示例

ZMQ版本：4.2.5

编译工具：clang

编译

`clang -Wall server.c -o server -L/usr/local/lib -lzmq`

`clang -Wall client.c -o client -L/usr/local/lib -lzmq`

启动server（CTRL + C杀死）
`./server`

执行client
`./client`