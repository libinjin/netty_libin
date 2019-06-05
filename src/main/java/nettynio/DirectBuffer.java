package nettynio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DirectBuffer {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/file/aa.txt");
        FileOutputStream fos = new FileOutputStream("src/main/resources/file/cc.txt");

        FileChannel inputchannel = fis.getChannel();
        FileChannel outputChannel = fos.getChannel();

        //直接缓冲区分配，分配系统内存，在JVM内存之外
        ByteBuffer buffer = ByteBuffer.allocateDirect(512);

        while(true){
            buffer.clear();
            int read = inputchannel.read(buffer);
            System.out.println("read:"+read);
            if(-1 == read){
                break;
            }
            buffer.flip();

            outputChannel.write(buffer);
        }
        inputchannel.close();
        outputChannel.close();
    }
}
