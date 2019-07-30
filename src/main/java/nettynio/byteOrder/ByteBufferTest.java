package nettynio.byteOrder;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;

public class ByteBufferTest {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        lock.tryLock();

        ByteBuffer directBuffer = ByteBuffer.allocateDirect(100);

        System.out.println(directBuffer.isDirect());

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        byteBuffer.asReadOnlyBuffer();

        System.out.println("1:"+byteBuffer);

        byteBuffer.put(3,(byte) 9);

        System.out.println("2:"+byteBuffer);

        byteBuffer.putInt(5);

        System.out.println("2:"+byteBuffer);

        byteBuffer.flip();

        System.out.println("3:"+byteBuffer);

        int a = byteBuffer.getInt();

        System.out.println(a);
    }
}
