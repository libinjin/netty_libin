package nettynio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
/**
 * 类型化得put与get
 */
public class BufferPutGet {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //ByteBuffer buffer = ByteBuffer.allocate(640);

        /*for (int i = 0; i < 10; i++) {
            buffer.putInt(15);
            buffer.putLong(50000000000L);
            buffer.putDouble(14.12323);
            buffer.putChar('你');
            buffer.putShort((short) 2);
            buffer.putChar('我');

        }*/
        //分配一个HeapByteBuffer的实例，其底层是byte数组
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);

        byte[] bytes = "你好".getBytes("utf-8");
        byteBuffer.putDouble(2.35);
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.putDouble(3.35);


        byteBuffer.flip();
        double d = byteBuffer.getDouble();
        System.out.println(d);
        byte[] temp = new byte[byteBuffer.getInt()];
        byteBuffer.get(temp);
        System.out.println(new String(temp,"utf-8"));
        double c = byteBuffer.getDouble();
        System.out.println(c);
        //buffer.flip();
        /**
         * 适用于知道明确协议
         * 读取的时候直接按下标位置去读取
         */

    /*    for (int i = 0; i < 10; i++) {

            System.out.println(buffer.getInt());
            System.out.println(buffer.getLong());
            System.out.println(buffer.getDouble());
            System.out.println(buffer.getChar());
            System.out.println(buffer.getShort());
            System.out.println(buffer.getChar());
        }*/

    }
}
