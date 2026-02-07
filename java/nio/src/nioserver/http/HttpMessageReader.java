package nioserver.http;

import nioserver.message.io.IMessageReader;
import nioserver.Socket;
import nioserver.message.Message;
import nioserver.message.MessageBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class HttpMessageReader implements IMessageReader {
    // The shared message buffer
    private MessageBuffer sharedMessageBuffer;
    // Message currently being read (HTTP messages may arrive in multiple chunks)
    private Message nextMessage;
    // Messages we finished reading (HTTP 1.1 pipelining sends multiple messages over one connection)
    private List<Message> completedMessages;

    public HttpMessageReader(MessageBuffer messageBuffer) {
        this.sharedMessageBuffer = messageBuffer;
        this.nextMessage = messageBuffer.getMessage();
        this.nextMessage.metaData = new HttpHeaders();
        this.completedMessages = new ArrayList<>();
    }

    @Override
    public void read(Socket socket, ByteBuffer byteBuffer) throws IOException {
        // data: socket ---> buffer
        int bytesRead = socket.read(byteBuffer);
        if (bytesRead == 0) {
            return; // nothing to read from byteBuffer
        }

        // date: buffer ---> message (byte array)
        byteBuffer.flip();
        this.nextMessage.writeToMessage(byteBuffer);

        // loop until all complete messages are read
        while (true) {
            int endIndex = HttpUtil.parseHttpRequest(
                    this.nextMessage.sharedByteArray, this.nextMessage.offset, this.nextMessage.offset + this.nextMessage.length, (HttpHeaders) this.nextMessage.metaData);

            boolean complete = endIndex != -1;
            if (!complete) {
                break;
            }

            Message message = this.sharedMessageBuffer.getMessage();
            message.metaData = new HttpHeaders();
            message.writePartialMessageToMessage(this.nextMessage, endIndex);

            this.nextMessage.socketId = socket.socketId;
            this.completedMessages.add(this.nextMessage);
            this.nextMessage = message;
        }
        byteBuffer.clear();
    }

    @Override
    public List<Message> getMessages() {
        return completedMessages;
    }
}
