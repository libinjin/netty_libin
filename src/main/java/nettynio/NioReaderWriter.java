package nettynio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 */
public class NioReaderWriter {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/file/aa.txt");
        FileOutputStream fos = new FileOutputStream("src/main/resources/file/bb.txt");

        FileChannel inputChannel  = fis.getChannel();
        FileChannel outputChannel = fos.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true){
            byteBuffer.clear();
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
    }
}
