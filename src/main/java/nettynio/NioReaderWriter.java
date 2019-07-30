package nettynio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 *
 */
public class NioReaderWriter {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/file/aa.txt");
        FileOutputStream fos = new FileOutputStream("src/main/resources/file/bb.txt");

        FileChannel inputChannel  = fis.getChannel();
        FileChannel outputChannel = fos.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        while (true){
            /**
             * 可以将position置为0，
             * 将limit置成capacity
             */
            byteBuffer.clear();
            /**
             * read时，因为byteBuffer的总大小为27，所以就直接把所有的读到
             * 了byteBuffer里面，当byteBuffer小于27时，就分批次读取
             *
             */
            int read = inputChannel.read(byteBuffer);
            System.out.println("read:"+read);
            if (-1 == read){
                break;
            }
            byteBuffer.flip();
            outputChannel.write(byteBuffer);
        }
        inputChannel.close();
        outputChannel.close();

        SocketChannel.open();
    }
}
