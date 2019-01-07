package nettynio;

import java.nio.ByteBuffer;

/**
 *我们可以随时讲一个buffer调用
 * asReadOnlyBuffer方法返回一个只读buffer
 * 但是不能经一个只读buffer转换为读写buffer
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        ByteBuffer readonlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readonlyBuffer.getClass());
        readonlyBuffer.position(0);
        readonlyBuffer.put((byte) 2);
    }
}
