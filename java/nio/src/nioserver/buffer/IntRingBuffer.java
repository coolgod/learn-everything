package nioserver.buffer;

public class IntRingBuffer {
    private int[] elements;
    private int capacity;

    private int readPos = 0;
    private int writePos = 0;

    private boolean flipped = false;

    public IntRingBuffer(int capacity) {
        this.elements = new int[capacity];
        this.capacity = capacity;
    }

    public void reset() {
        this.readPos = 0;
    }

    public int available() {
        return -1;
    }

    public boolean put(int i) {
        if (!flipped) {
            if (writePos == capacity) {
                writePos = 0;
                flipped = true;
                return putInFlippedMode(i);
            } else {
                elements[writePos++] = i;
                return true;
            }
        } else {
            return putInFlippedMode(i);
        }
    }

    private boolean putInFlippedMode(int i) {
        if (writePos < readPos) {
            elements[writePos++] = i;
            return true;
        } else {
            return false;
        }
    }

    public int take() {
        if (!flipped) {
            return takeInNonFlippedMode();
        } else {
            if (readPos == capacity) {
                flipped = false;
                return takeInNonFlippedMode();
            } else {
                return elements[readPos++];
            }
        }
    }

    private int takeInNonFlippedMode() {
        if (readPos < writePos) {
            return elements[readPos++];
        } else {
            return -1;
        }
    }
}
