package nettynio.byteOrder;

import java.nio.ByteBuffer;

public class TestBytebuffer {

    public static void main(String[] args) {
        System.out.println("--------Test reset----------");
        ByteBuffer buffer = ByteBuffer.allocate(15);

/*        buffer.position(3);
        buffer.mark();//在下标是3的位置上做标记
        buffer.position(8);//将buffer的位置置到8
        System.out.println(buffer+"--------------");
        buffer.reset();
        System.out.println(buffer+"--------------");*/


   /*     buffer.clear();
        buffer.position(9);
        buffer.limit(10);
        System.out.println("before rewind:" + buffer);
        buffer.rewind();
        System.out.println("before rewind:" + buffer);*/

        System.out.println("--------Test compact--------");
        buffer.clear();
        buffer.put("abcd".getBytes());
        System.out.println("before compact:" + buffer);
        System.out.println(new String(buffer.array()));
        buffer.flip();
        System.out.println("after flip:" + buffer);
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println("after three gets:" + buffer);
        System.out.println("\t" + new String(buffer.array()));
        buffer.compact();
        System.out.println("after compact:" + buffer);
        System.out.println("\t" + new String(buffer.array()));
    }
}
