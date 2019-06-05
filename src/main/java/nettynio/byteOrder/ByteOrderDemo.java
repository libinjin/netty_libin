package nettynio.byteOrder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * ByteOrder定义了写入buffer时字节的顺序
 *
 * java默认是big-endian
 *
 * 两个内置的ByteOrder
 * ByteOrder.BIG_ENDIAN和ByteOrder.LITTLE_ENDIAN
 *
 * 返回本地JVM运行的硬件的字节顺序，使用和硬件一致的字节顺序
 * 可能使buffer更加有效
 *
 * ByteOrder.toString()
 *
 * 返回ByteOrder的名字,BIG_ENDIAN或LITTLE_ENDIAN
 */

public class ByteOrderDemo {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(4);

        System.out.println("默认的顺序："+buffer.order().toString());

        buffer.putShort((short) 1);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        System.out.println(buffer.order().toString());
        buffer.putShort((short)2);

        buffer.flip();
        for(int i=0;i<buffer.limit();i++)
            System.out.println(buffer.get()&0xFF);

        System.out.println("My PC: "+ByteOrder.nativeOrder().toString());
    }
}
