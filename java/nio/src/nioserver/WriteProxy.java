package nioserver;

import nioserver.message.Message;
import nioserver.message.MessageBuffer;

import java.util.Queue;

public class WriteProxy {
    private MessageBuffer writeMessageBuffer = null;
    private Queue<Message> writeQueue = null;

    public WriteProxy(MessageBuffer messageBuffer, Queue<Message> writeQueue) {
        this.writeMessageBuffer = messageBuffer;
        this.writeQueue = writeQueue;
    }

    public Message getMessage(){
        return this.writeMessageBuffer.getMessage();
    }

    public boolean enqueue(Message message){
        return this.writeQueue.offer(message);
    }
}
