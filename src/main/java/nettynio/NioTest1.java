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
        for (int i = 0; i <buffer.capacity() ; i++) {
            int randomNum = new SecureRandom().nextInt(20);
            //向buffer中写入数据
            buffer.put(randomNum);
        }
        //反转后，从buffer中读取数据
        buffer.flip();
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
