package nioserver;

import nioserver.http.HttpMessageReader;
import nioserver.http.HttpMessageWriter;
import nioserver.message.Message;
import nioserver.message.MessageBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class SocketProcessor implements Runnable {
    private enum SocketState {
        PENDING_REGISTRATION,
        REGISTERED
    }

    private Queue<Socket> inboundSocketQueue = null;

    // global queue: all outbound messages for all sockets
    private Queue<Message> outboundMessageQueue = new LinkedList<>();

    private long nextSocketId = 16 * 1024;

    // This ensures read and write are bound to the same socket (by socket id)
    private Map<Long, Socket> socketMap = new HashMap<>();

    // This tracks which socket we need to listen to for writing messages
    private Map<Socket, SocketState> socketWriteStateMap = new HashMap<>();

    private MessageBuffer readMessageBuffer = null;
    private MessageBuffer writeMessageBuffer = null;

    private Selector readSelector;
    private Selector writeSelector;

    private ByteBuffer readByteBuffer  = ByteBuffer.allocate(128 * 1024 * 1024);
    private ByteBuffer writeByteBuffer = ByteBuffer.allocate(128 * 1024 * 1024);

    private IMessageProcessor messageProcessor;
    private WriteProxy writeProxy;

    public SocketProcessor(
            Queue<Socket> inboundSocketQueue,
            MessageBuffer readMessageBuffer,
            MessageBuffer writeMessageBuffer,
            IMessageProcessor messageProcessor
    ) throws IOException {
        this.inboundSocketQueue = inboundSocketQueue;
        this.readMessageBuffer = readMessageBuffer;
        this.writeMessageBuffer = writeMessageBuffer;
        this.messageProcessor = messageProcessor;

        this.readSelector = Selector.open();
        this.writeSelector = Selector.open();
        this.writeProxy = new WriteProxy(this.writeMessageBuffer, outboundMessageQueue);
    }

    @Override
    public void run() {
        while (true) {
            try {
//                System.out.println("executeCycle...");
                executeCycle();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeCycle() throws IOException {
        takeNewSockets();
        readFromSockets();
        writeToSockets();
    }

    public void takeNewSockets() throws IOException {
        Socket newSocket = this.inboundSocketQueue.poll(); // non-blocking

        while (newSocket != null) {
            System.out.println("Sockets ready for read select");
            newSocket.socketId = this.nextSocketId++;
            newSocket.socketChannel.configureBlocking(false);
            newSocket.messageReader = new HttpMessageReader(this.readMessageBuffer);
            newSocket.messageWriter = new HttpMessageWriter();

            SelectionKey key = newSocket.socketChannel.register(this.readSelector, SelectionKey.OP_READ);
            key.attach(newSocket);

            this.socketMap.put(newSocket.socketId, newSocket);

            newSocket = this.inboundSocketQueue.poll();
        }
    }

    public void readFromSockets() throws IOException {
       int ready = this.readSelector.selectNow();
       if (ready > 0) {
           System.out.println("Sockets ready for read");
           // Same reference to the set every time we call `readSelector.selectedKeys`
           Set<SelectionKey> selectedKeys = readSelector.selectedKeys();
           Iterator<SelectionKey> iterator = selectedKeys.iterator();
           while (iterator.hasNext()) {
               SelectionKey key = iterator.next();
               readFromSocket(key);
               // Need removes it from the set to avoid duplicated read
               iterator.remove();
           }
       }
    }

    private void readFromSocket(SelectionKey selectionKey) throws IOException {
        Socket socket = (Socket) selectionKey.attachment();
        socket.messageReader.read(socket, this.readByteBuffer);

        List<Message> completedMessaged = socket.messageReader.getMessages();
        if (!completedMessaged.isEmpty()) {
            System.out.println(completedMessaged.size() + " messages read");
            for (Message message: completedMessaged) {
                messageProcessor.process(message, this.writeProxy);
            }
            completedMessaged.clear();
        }

        if (socket.endOfStreamReached) {
            System.out.println("Socket closed: " + socket.socketId);
            this.socketMap.remove(socket.socketId);
            selectionKey.attach(null);
            selectionKey.channel().close();
            selectionKey.cancel();
        }
    }

    public void writeToSockets() throws IOException {
        takeNewOutboundMessages();
        registerOutboundSockets();

        int ready = this.writeSelector.selectNow();
        if (ready > 0) {
            Set<SelectionKey> selectedKeys = this.writeSelector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                Socket socket = (Socket) key.attachment();
                socket.messageWriter.write(socket, this.writeByteBuffer);
                iterator.remove();
            }
        }
    }

    private void takeNewOutboundMessages() {


        while (!this.outboundMessageQueue.isEmpty()) {
            Message message = this.outboundMessageQueue.poll();
            if (message == null) {
                System.out.println("[Warning] Skipping null messages from outboundMessageQueue");
                continue;
            }
            Socket socket = this.socketMap.get(message.socketId);
            if (socket != null) {
                if (socket.messageWriter.isEmpty()) {
                    socketWriteStateMap.put(socket, SocketState.PENDING_REGISTRATION);
                }
                socket.messageWriter.enqueue(message);
            }
        }
    }

    private void registerOutboundSockets() throws ClosedChannelException {
        Set<Socket> toRemove = new HashSet<>();
        Set<Socket> toUpdate = new HashSet<>();
        for (Socket socket: this.socketWriteStateMap.keySet()) {
            SocketState state = this.socketWriteStateMap.get(socket);
            if (state == SocketState.REGISTERED && socket.messageWriter.isEmpty()) {
                SelectionKey key = socket.socketChannel.keyFor(this.writeSelector);
                if (key != null) {
                    key.cancel();
                }
                toRemove.add(socket);
            }
            if (state == SocketState.PENDING_REGISTRATION) {
                socket.socketChannel.register(this.writeSelector, SelectionKey.OP_WRITE, socket);
                toUpdate.add(socket);
            }
        }
        for (Socket socket: toRemove) {
            this.socketWriteStateMap.remove(socket);
        }
        for (Socket socket: toUpdate) {
            this.socketWriteStateMap.put(socket, SocketState.REGISTERED);
        }
    }
}
