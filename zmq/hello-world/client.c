#include <zmq.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>

int main(void) {
    void *context = zmq_init(1);

    // 创建套接字
    printf("正在连接至服务器...\n");
    void *socket = zmq_socket(context, ZMQ_REQ);
    zmq_connect(socket, "tcp://localhost:5555");

    // 依次发送10个请求并接收回应
    int request_nbr;
    for(request_nbr = 0; request_nbr < 10; request_nbr++) {
        // 发送请求
        zmq_msg_t request;
        zmq_msg_init_size(&request, 5);
        memcpy(zmq_msg_data(&request), "Hello", 5);
        printf("正在发送Hello %d...\n", request_nbr);
        zmq_msg_send(&request, socket, 0);
        zmq_msg_close(&request);

        // 接收服务器返回response
        zmq_msg_t response;
        zmq_msg_init(&response);
        zmq_msg_recv(&response, socket, 0);
        // 将接收到的纯二进制数据转换成string
        int response_size = zmq_msg_size(&response);
        char *response_string = malloc(response_size + 1);
        memcpy(response_string, zmq_msg_data(&response), response_size);
        // 打印
        printf("接收到 %s %d\n", response_string, request_nbr);
        zmq_msg_close(&response);
    }

    zmq_close(socket);
    zmq_term(context);
    return 0;
}