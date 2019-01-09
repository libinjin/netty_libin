package nettynio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.List;

/**
 * 对于方便按照某种协议
 * 来读取或者写入一组固定
 * 长度的数据
 */
public class ScatteringAndGather {
    public static void main(String[] args) throws IOException {
        //开启socket服务器端
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 + 4;
        //构造ByteBuffer数组
        ByteBuffer[] buffers = new ByteBuffer[3];
        //给buffers数组里面放置不同的buffer
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        /**
         * 这里就发生阻塞，一直等待
         * 连接进来才能往下走
         */
        System.out.println("创建连接");
        SocketChannel socketChannel = serverSocketChannel.accept();

        //死循环
        while(true){
            int byteRead = 0;
            while(byteRead < messageLength){

                long r = socketChannel.read(buffers);
                byteRead += r;
                System.out.println("byteRead:"+byteRead);
                List<ByteBuffer> list = Arrays.asList(buffers);
                for (ByteBuffer buffer : list){
                    int position = buffer.position();
                    int limit = buffer.limit();
                    System.out.println("position:"+position+",limit:"+limit);
                }
            }
            List<ByteBuffer> list = Arrays.asList(buffers);
            for (ByteBuffer buffer : list){
                buffer.flip();
            }
            long byteWritten = 0;
            while (byteWritten < messageLength){
                long r = socketChannel.write(buffers);
                byteWritten += r;
            }
            for (ByteBuffer buffer : list){
                buffer.clear();
            }

        }

    }
}
