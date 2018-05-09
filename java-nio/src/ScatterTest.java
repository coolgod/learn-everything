import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScatterTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("scatter-test.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buffer1 = ByteBuffer.allocate(6); // 包括换行符
        ByteBuffer buffer2 = ByteBuffer.allocate(6);
        ByteBuffer buffer3 = ByteBuffer.allocate(6);

        ByteBuffer[] bufferArray = { buffer1, buffer2, buffer3 };
        inChannel.read(bufferArray);

        for (int i = 0; i < bufferArray.length; i++) {
            System.out.println("read from buffer " + i);
            bufferArray[i].flip();
            while(bufferArray[i].hasRemaining()) {
                System.out.print((char) bufferArray[i].get());
            }
        }
        inChannel.close();
    }
}
