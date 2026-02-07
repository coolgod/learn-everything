package nioserver.http;

import nioserver.Socket;
import nioserver.message.Message;
import nioserver.message.io.IMessageWriter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class HttpMessageWriter implements IMessageWriter {
    final private List<Message> queue = new ArrayList<>();
    private Message nextMessage;
    private int bytesWritten = 0;

    @Override
    public void enqueue(Message message) {
        if (nextMessage != null) {
            queue.add(message);
        } else {
            this.nextMessage = message;
        }
    }

    @Override
    public void write(Socket socket, ByteBuffer byteBuffer) throws IOException {
        // data: nextMessage.sharedByteArray -> byteBuffer
        byteBuffer.put(this.nextMessage.sharedByteArray, this.nextMessage.offset + this.bytesWritten, this.nextMessage.length - this.bytesWritten);
        byteBuffer.flip();

        this.bytesWritten += socket.write(byteBuffer);
        byteBuffer.clear();

        if (bytesWritten >= this.nextMessage.length) {
            if (queue.isEmpty()) {
                this.nextMessage = null;
            } else {
                this.nextMessage = queue.removeFirst();
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.nextMessage == null && this.queue.isEmpty();
    }
}
