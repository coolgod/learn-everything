package nioserver.message;

import java.nio.ByteBuffer;

public class Message {

    private MessageBuffer srcMessageBuffer = null;

    public long socketId = 0;

    public byte[] sharedByteArray = null;
    public int offset = 0;
    public int length = 0;
    public int capacity = 0;

    public Object metaData;

    public Message(MessageBuffer srcMessageBuffer) {
        this.srcMessageBuffer = srcMessageBuffer;
    }

    private void expandMessage(int sizeNeeded) {
        while (this.length + sizeNeeded > this.capacity) {
            boolean expanded = this.srcMessageBuffer.expandMessage(this);
            if (!expanded) {
                throw new RuntimeException("Size too big, failed to write message");
            }
        }
    }

    public int writeToMessage(ByteBuffer byteBuffer) {
        int size = byteBuffer.remaining();
        expandMessage(size);

        byteBuffer.get(this.sharedByteArray, this.offset + this.length, size);
        this.length += size;

        return size;
    }

    public int writeToMessage(byte[] newData) {
        return writeToMessage(newData, 0, newData.length);
    }

    public int writeToMessage(byte[] newData, int offset, int size) {
        this.expandMessage(size);
        System.arraycopy(newData, offset, this.sharedByteArray, this.offset + this.length, size);
        this.length += size;
        return size;
    }

    public void writePartialMessageToMessage(Message message, int endIndex) {
        int size = message.offset + message.length - endIndex;
        System.arraycopy(message.sharedByteArray, endIndex, this.sharedByteArray, this.offset + this.length, size);
        this.length += size;
    }
}
