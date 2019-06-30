package nettynio.byteOrder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteOrderDemo2 {

    public static void main(String[] args) {
        System.out.println("当前系统order="+ByteOrder.nativeOrder());
        ByteBuffer buffer = ByteBuffer.allocate(20);

        // 获取默认的byte顺序
        ByteOrder order = buffer.order(); //
        System.out.println("当前order="+order);

        ByteOrder order2 = buffer.order(); //
        System.out.println("当前order2="+order2);

        ByteBuffer newOrder = buffer.order(ByteOrder.LITTLE_ENDIAN);

        System.out.println("当前order="+newOrder.order());

        buffer.putShort(0, (short) 1);
        buffer.get(0);
        System.out.println("此时取出0:"+buffer.get(0));

        buffer.get(1);
        System.out.println("此时取出1:"+buffer.get(1));


    }
}
