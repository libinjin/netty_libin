package nettynio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.RandomAccess;

/**
 * 内存映射文件
 * 直接修改的堆外内存
 */
public class MappedByte {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("src/main/resources/file/aa.txt","rw");
        FileChannel channel = accessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE,0,5);
        mappedByteBuffer.put(0,(byte) 'a');
        mappedByteBuffer.put(1,(byte) 'b');
        mappedByteBuffer.put(2,(byte) 'c');
        accessFile.close();
    }
}
