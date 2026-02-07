import nioserver.IMessageProcessor;
import nioserver.Server;
import nioserver.message.Message;

import java.io.IOException;

public class Main {
    private static final int PORT = 3000;

    private static final byte[] HELLO_WORLD_RESPONSE_BYTES = """
    HTTP/1.1 200 OK
    Content-Length: 12
    Content-Type: text/html
    
    Hello World!
    """.getBytes();

    public static void main(String[] args) throws IOException {
        IMessageProcessor messageProcessor = (requestMessage, writeProxy) -> {
            System.out.println("Message Received from socket: " + requestMessage.socketId);
            Message responseMessage = writeProxy.getMessage();
            responseMessage.socketId = requestMessage.socketId;
            responseMessage.writeToMessage(HELLO_WORLD_RESPONSE_BYTES);

            writeProxy.enqueue(responseMessage);
        };

        Server server = new Server(PORT, messageProcessor);
        server.start();
        System.out.println("Server started at port: " + PORT);
    }
}
