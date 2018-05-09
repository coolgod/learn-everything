import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {
    public static void main(String[] args) throws Exception{
        RandomAccessFile aFile = new RandomAccessFile("test.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf); // 从channel读取内容，放入buffer
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead + " bytes");
            buf.flip(); // 准备从buffer中读出内容

            while(buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
