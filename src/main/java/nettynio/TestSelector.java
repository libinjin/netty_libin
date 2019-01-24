package nettynio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TestSelector {
    public static void main(String[] args) throws IOException {

        //创建服务器，先拿到serverSocketChannel,然后得到serverSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(6880));
        //拿到Selector
        Selector selector = Selector.open();
        //将channel注册到Selector上面
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //监听连接
        while (true){
            //监听客户端发送的过来的事件
            selector.select();
            //SelectionKey,不仅可以通过它获取到事件的集合，而且可以根据它得到相应的事件
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            selectionKeySet.size();
            //拿出具体的事件
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //如果是连接事件
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = server.accept();
                    //将其设置为异步
                    socketChannel.configureBlocking(false);
                    //接着将读事件注册到socketChannel上

                    socketChannel.register(selector, selectionKey.OP_READ);
                    iterator.remove();
                }else if(selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    while(true){
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);
                        if(read <= 0){
                            break;
                        }
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                    }
                    iterator.remove();
                }
            }
        }

    }
}
