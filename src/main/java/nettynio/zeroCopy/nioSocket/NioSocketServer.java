package nettynio.zeroCopy.nioSocket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioSocketServer {
    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        /**
         * 如果8899以前被别的sever绑定过，那么在关闭后的一段时间内
         * 它是处于time_wait状态，其他sever是不能绑定8899的
         * 设置了servereSocket.setReuseAddress(true)后，
         * 就可以立即使用而不受上次绑定的影响。
         */
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(8899));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端："+8899+"开启");

        String fileName = "E:\\视频\\cc.mp4";
        FileOutputStream fos = new FileOutputStream(fileName);
        FileChannel fileChannel = fos.getChannel();

        while(true){

            selector.select();//看那个channel有数据，就绑定该channel到key上

            Set<SelectionKey> set = selector.selectedKeys();

            for (SelectionKey selectionKey : set ) {
                SocketChannel client;
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector,SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){
                    client = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.clear();
                    int cout = client.read(buffer);
                    if(cout<=0){
                        break;
                    }
                    buffer.flip();
                    fileChannel.write(buffer);

                }

            }
            set.clear();
        }
    }
}
