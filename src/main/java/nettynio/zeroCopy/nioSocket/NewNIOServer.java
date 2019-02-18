package nettynio.zeroCopy.nioSocket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewNIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(8888));
        System.out.println("服务端开始");
        String fileName ="E:\\视频\\cc.mp4";
        FileChannel fileChannel = new FileOutputStream(fileName).getChannel();
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            /*int read = 0;
            while (-1 != read){
                read = socketChannel.read(byteBuffer);
                byteBuffer.rewind();
            }*/
            while(true){
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.clear();
                int cout = socketChannel.read(buffer);
                if(cout<=0){
                    break;
                }
                buffer.flip();
                fileChannel.write(buffer);
            }
            System.out.println("发送完毕");
        }
    }
}
