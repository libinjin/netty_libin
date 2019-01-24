package nettynio.nioSocket.charset;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.SortedMap;

/**
 * 字符集编解码
 */
public class CharSetDemo {
    public static void main(String[] args) throws IOException {
        String inputFile = "src/main/resources/file/input.txt";
        String outputFile = "src/main/resources/file/outputFile.txt";
        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile,"r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile,"rw");

        long length = new File(inputFile).length();

        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        //内存映射文件，直接可以修改堆外内存
        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY,0,length);
        //得到系统所有的字符集
        SortedMap<String,Charset> sortedMap = Charset.availableCharsets();

        /**
         * Charset.forName("iso-8859-1");
         * 为什么用iso-8859-1也可以进行正确的中文编码
         * 你好
         * AB DC EF AC DF FG
         * 在iso-8859-1编码时，直接将原来解码的字符原封不动的
         * 进行编码到另外一个文件里面
         *
         */
        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();//把字节数组转为字符串，解码
        CharsetEncoder encoder = charset.newEncoder();//把字符串转为字节数组，编码

        //先把文件解码成charBuffer
        CharBuffer charBuffer = decoder.decode(inputData);
        System.out.println(charBuffer.get(0));
        System.out.println(String.valueOf(charBuffer.array()));
        //再将charBuffer以utf-8的形式编程，最后写入到outputFileChannel中
        //ByteBuffer outputData = encoder.encode(charBuffer);
        //这时会乱码
        ByteBuffer outputData = Charset.forName("iso-8859-1").encode(charBuffer);
        outputFileChannel.write(outputData);

        inputRandomAccessFile.close();
        outputRandomAccessFile.close();
    }
}
