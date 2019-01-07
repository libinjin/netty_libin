package nettynio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 传统的io如何调用nio
 * 来实现
 */
public class NioTest2 {
    public static void main(String[] args) throws IOException {

        FileInputStream fis = new FileInputStream("src/main/resources/file/aa.txt");
        //通过nio方式把文件读取到程序当中
        FileChannel fileChannel = fis.getChannel();
        //buffer的大小直接有ByteBuffer.allocate()分配
        //
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //把数据读到buffer中，然后从buffer中取出数据
        fileChannel.read(buffer);
       //需要先反转
        buffer.flip();
        while (buffer.hasRemaining()){
            byte b = buffer.get();
            System.out.print((char)b);
        }
        fis.close();
        fileChannel.close();
    }
}
