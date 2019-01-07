package nettynio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 *
 */
public class NioTest1 {
    public static void main(String[] args) {
        //分配一个大小为10的缓冲区
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("capaticy:"+buffer.capacity());

        for (int i = 0; i <5; i++) {
            int randomNum = new SecureRandom().nextInt(20);
            //向buffer中写入数据
            buffer.put(randomNum);
        }
        System.out.println("buffer的limit："+buffer.limit());
        //反转后，从buffer中读取数据
        buffer.flip();
        System.out.println("buffer翻转后的limit大小："+buffer.limit());
        while(buffer.hasRemaining()){
            System.out.println("position:"+buffer.position());
            System.out.println("limit:"+buffer.limit());
            System.out.println("capacity:"+buffer.capacity());

            System.out.println(buffer.get());
        }
    }
}
