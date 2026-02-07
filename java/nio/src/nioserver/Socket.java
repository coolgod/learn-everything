package nioserver;

import nioserver.message.io.IMessageReader;
import nioserver.message.io.IMessageWriter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Socket {
    public long socketId = 0;
    public SocketChannel socketChannel = null;
    public IMessageReader messageReader = null;
    public IMessageWriter messageWriter = null;

    public boolean endOfStreamReached = false;

    public Socket() {}

    public Socket(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        System.out.println("reading socket " + this.socketId);
        int bytesRead = this.socketChannel.read(byteBuffer);
        int totalBytesRead = bytesRead;

        while (bytesRead > 0) {
            bytesRead = this.socketChannel.read(byteBuffer);
            totalBytesRead += bytesRead;
        }

        System.out.println("reading socket " + this.socketId + ", bytes read " + totalBytesRead);
        // This happens when the client closes the connection (sends TCP FIN).
        // The channel signals "no more data will ever come" by returning -1.
        if (bytesRead == -1) {
            this.endOfStreamReached = true;
        }

        return totalBytesRead;
    }

    public int write(ByteBuffer byteBuffer) throws IOException {
        int bytesWritten = this.socketChannel.write(byteBuffer);
        int totalBytesWritten = bytesWritten;

        while (bytesWritten > 0 && byteBuffer.hasRemaining()) {
            bytesWritten = this.socketChannel.write(byteBuffer);
            totalBytesWritten += bytesWritten;
        }

        return totalBytesWritten;
    }
 }
