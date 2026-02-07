package nioserver;

import nioserver.message.Message;

public interface IMessageProcessor {
    public void process(Message message, WriteProxy writeProxy);
}
