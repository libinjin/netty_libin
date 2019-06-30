package nettynio.byteOrder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ByteOrderDemo3 {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.wrap(new byte[12]);
        buffer.asCharBuffer().put("abcd");
        System.out.println(Arrays.toString(buffer.array()));

        buffer.rewind();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.asCharBuffer().put("abcd");
        System.out.println(Arrays.toString(buffer.array()));
        System.out.println("-------------------------------");

        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.asCharBuffer().put("abcd");
        System.out.println(buffer);
        System.out.println(Arrays.toString(buffer.array()));
        System.out.println("-------------------------------");

        System.out.println(buffer);

        System.out.println("\t" + new String(buffer.array()));



/*
        ByteBuffer buf = ByteBuffer.wrap(new byte[8]);
        buf.asLongBuffer().put(9);
        System.out.println("默认的字节存放顺序:"+Arrays.toString(buf.array()) +"\n当前字节存放方式："+buf.order());

        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.asLongBuffer().put(9);
        System.out.println("改后的字节存放顺序:"+Arrays.toString(buf.array()) +"\n改后字节存放方式："+buf.order());
*/


    }
}
