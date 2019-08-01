package nettynio.zeroCopy.nioSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioSocketClient {

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 8899;

        InetSocketAddress address = new InetSocketAddress(host, port);

        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);//设为异步

        System.out.println("开始连接服务器端");

        boolean b = socketChannel.connect(address);

        System.out.println(b==true?"连接成功":"连接失败");

        while(!socketChannel.finishConnect()){
            socketChannel.write(ByteBuffer.allocate(10).put("sdf".getBytes("UTF-8")));
        }
    }
}
