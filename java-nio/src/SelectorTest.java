import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {
    public static void main(String[] args) throws Exception {
        // 创建ServerSocketChannel监听Incoming TCP
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(9000));
        channel.configureBlocking(false);

        // 创建Selector来监听channel
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {
            int readyChannels = selector.select();

            if(readyChannels == 0) continue;

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if(key.isAcceptable()) {
                    System.out.println("the channel is acceptable");
                } else if (key.isConnectable()) {
                    System.out.println("the channel is connectable");
                } else if (key.isReadable()) {
                    System.out.println("the channel is readable");
                } else if (key.isWritable()) {
                    System.out.println("the channel is writable");
                }

                keyIterator.remove();
                channel.close();
            }
        }
    }
}
