package nettynio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioWrite {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("src/main/resources/file/aa.txt");
        FileChannel  fileChannel = fos.getChannel();
        //这里分配好的capacity的值时永远不会变的
        ByteBuffer buffer = ByteBuffer.allocate(512);
        byte[] date = "年后的很多很多的con".getBytes("UTF-8");
        buffer.put(date);
        buffer.flip();
        fileChannel.write(buffer);
        fos.close();
    }
}
