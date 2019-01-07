package nettynio;

import java.nio.ByteBuffer;

/**
 * 分割或者分片buffer，slice
 * 有一个buffer，长度为10，从第二个位置
 * 到第六个位置，调用slice方法得到的buffer是
 * 原来buffer的快照
 * 底层操作的是同一个buffer
 *
 */
public class SliceBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i <buffer.capacity() ; i++) {
            buffer.put((byte) i);
        }
        buffer.position(2);
        buffer.limit(6);
        //操作的还是原来的buffer
        ByteBuffer sliceBuffer = buffer.slice();

        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i,b);
        }
        buffer.position(0);
        buffer.limit(buffer.capacity());
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
