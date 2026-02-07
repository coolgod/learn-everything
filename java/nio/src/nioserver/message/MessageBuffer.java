package nioserver.message;

import nioserver.buffer.IntRingBuffer;

public class MessageBuffer {
    // number of bytes
    final private static int KB = 1024;
    final private static int MB = 1024 * KB;

    final private static int BLOCK_SIZE_SMALL = 4 * KB;
    final private static int BLOCK_SIZE_MEDIUM = 128 * KB;
    final private static int BLOCK_SIZE_LARGE = MB;

    final private static int CAPACITY_SMALL = 1024;
    final private static int CAPACITY_MEDIUM = 128;
    final private static int CAPACITY_LARGE = 16;

    final private byte[] smallMessageBuffer = new byte[CAPACITY_SMALL * BLOCK_SIZE_SMALL];
    final private byte[] mediumMessageBuffer = new byte[CAPACITY_MEDIUM * BLOCK_SIZE_MEDIUM];
    final private byte[] largeMessageBuffer = new byte[CAPACITY_LARGE * BLOCK_SIZE_LARGE];

    final private IntRingBuffer smallMessageBlocks = new IntRingBuffer(CAPACITY_SMALL);
    final private IntRingBuffer mediumMessageBlocks = new IntRingBuffer(CAPACITY_MEDIUM);
    final private IntRingBuffer largeMessageBlocks = new IntRingBuffer(CAPACITY_LARGE);

    public MessageBuffer() {
        // each "block" is represented by a starting offset
        for (int offset = 0; offset < smallMessageBuffer.length; offset += BLOCK_SIZE_SMALL) {
            smallMessageBlocks.put(offset);
        }
        for (int offset = 0; offset < mediumMessageBuffer.length; offset += BLOCK_SIZE_MEDIUM) {
            mediumMessageBlocks.put(offset);
        }
        for (int offset = 0; offset < largeMessageBuffer.length; offset += BLOCK_SIZE_LARGE) {
            largeMessageBlocks.put(offset);
        }
    }

    public Message getMessage() {
        int nextFreeSmallBlockOffset = smallMessageBlocks.take();
        if (nextFreeSmallBlockOffset == -1) {
            throw new RuntimeException("MessageBuffer overflow!");
        }

        Message message = new Message(this);
        message.sharedByteArray = smallMessageBuffer;
        message.capacity = BLOCK_SIZE_SMALL;
        message.offset = nextFreeSmallBlockOffset;
        message.length = 0;

        return message;
    }

    public boolean expandMessage(Message message) {
        if (message.capacity == BLOCK_SIZE_SMALL) {
            return moveMessage(message, smallMessageBlocks, mediumMessageBlocks, mediumMessageBuffer, BLOCK_SIZE_MEDIUM);
        } else if (message.capacity == BLOCK_SIZE_MEDIUM) {
            return moveMessage(message, mediumMessageBlocks, largeMessageBlocks, largeMessageBuffer, BLOCK_SIZE_LARGE);
        } else {
            return false;
        }
    }

    private boolean moveMessage(Message message, IntRingBuffer srcBlocks, IntRingBuffer destBlocks, byte[] destBuffer, int newBlockSize) {
        int nextFreeBlockOffset = destBlocks.take();
        if (nextFreeBlockOffset == -1) {
            throw new RuntimeException("MessageBuffer overflow!");
        }

        System.arraycopy(message.sharedByteArray, message.offset, destBuffer, nextFreeBlockOffset, message.length);

        // free smaller blocks
        srcBlocks.put(message.offset);

        message.sharedByteArray = destBuffer;
        message.capacity = newBlockSize;
        message.offset = nextFreeBlockOffset;

        return true;
    }
}
