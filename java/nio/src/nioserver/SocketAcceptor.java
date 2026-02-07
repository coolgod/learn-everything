package nioserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;

public class SocketAcceptor implements Runnable {
    private int tcpPort = 0;

    final private Queue<Socket> socketQueue;

    public SocketAcceptor(int tcpPort, Queue<Socket> socketQueue) {
        this.tcpPort = tcpPort;
        this.socketQueue = socketQueue;
    }

    @Override
    public void run() {
        ServerSocketChannel serverSocketChannel;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(this.tcpPort));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept(); // blocking
                System.out.println("Socket accepted: " + socketChannel);

                this.socketQueue.add(new Socket(socketChannel)); // throws exception if queue is full
            } catch (IOException e) {
                e.printStackTrace();;
            }
        }
    }
}
