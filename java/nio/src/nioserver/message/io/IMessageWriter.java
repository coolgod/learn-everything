package nioserver.message.io;

import nioserver.Socket;
import nioserver.message.Message;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface IMessageWriter {
    public void enqueue(Message message);
    public void write(Socket socket, ByteBuffer byteBuffer) throws IOException;
    public boolean isEmpty();
}
