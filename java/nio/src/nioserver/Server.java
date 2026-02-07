package nioserver;

import nioserver.message.MessageBuffer;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {
    private SocketAcceptor socketAcceptor = null;
    private SocketProcessor socketProcessor = null;

    private int tcpPort = 0;
    private IMessageProcessor messageProcessor = null;

    public Server(int tcpPort, IMessageProcessor messageProcessor) {
        this.tcpPort = tcpPort;
        this.messageProcessor = messageProcessor;
    }

    public void start() throws IOException {
        Queue<Socket> socketQueue = new ArrayBlockingQueue<Socket>(1024);

        this.socketAcceptor = new SocketAcceptor(this.tcpPort, socketQueue);

        MessageBuffer readBuffer = new MessageBuffer();
        MessageBuffer writeBuffer = new MessageBuffer();

        this.socketProcessor = new SocketProcessor(socketQueue, readBuffer, writeBuffer, messageProcessor);

        Thread acceptorThread = new Thread(this.socketAcceptor);
        Thread processorThread = new Thread(this.socketProcessor);

        acceptorThread.start();
        processorThread.start();
    }
}
