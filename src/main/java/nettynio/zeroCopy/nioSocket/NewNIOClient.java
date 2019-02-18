package nettynio.zeroCopy.nioSocket;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 零拷贝
 */
public class NewNIOClient {
    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8888));
        System.out.println("客户端连接成功");

        socketChannel.configureBlocking(true);
        String fileName ="E:\\视频\\aa.mp4";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        long end = System.currentTimeMillis();

        System.out.println("发送总字节数："+transferCount+"耗时："+(end-start));
        fileChannel.close();
    }
}
