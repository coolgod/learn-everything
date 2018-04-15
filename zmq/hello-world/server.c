#include <zmq.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int main(void) {
    void *context = zmq_init(1);

    // 创建socket
    void *socket = zmq_socket(context, ZMQ_REP);
    // 绑定socket
    zmq_bind(socket, "tcp://*:5555");

    // 无限循环，保持服务器监听状态
    while(1) {
        // 接收请求
        zmq_msg_t request;
        zmq_msg_init(&request);
        zmq_msg_recv(&request, socket, 0);
        // 将接收到的纯二进制数据转换成string
        int request_size = zmq_msg_size(&request);
        char *request_string = malloc(request_size + 1);
        memcpy(request_string, zmq_msg_data(&request), request_size);
        // 打印
        printf("收到 %s\n", request_string);
        zmq_msg_close(&request);

        // 等待1秒，模拟服务器处理延迟
        sleep(1);

        // 返回应答
        zmq_msg_t response;
        zmq_msg_init_size(&response, 5);
        memcpy(zmq_msg_data(&response), "World", 5);
        zmq_msg_send(&response, socket, 0);
        zmq_msg_close(&response);
    }

    // 关闭socket
    zmq_close(socket);
    zmq_term(context);
    return 0;
}