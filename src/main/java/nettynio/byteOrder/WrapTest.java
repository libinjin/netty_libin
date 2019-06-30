package nettynio.byteOrder;


import java.nio.CharBuffer;

public class WrapTest {

    public static void main(String[] args) {

        char[] array = new char[100];
        array[12] = 'a';
        array[13] = 'b';
        array[14] = 'c';



        CharBuffer buffer = CharBuffer.wrap(array , 10, 42);

        System.out.println("1:"+buffer.get());
        System.out.println("2:"+buffer.get());
        System.out.println("3:"+buffer.get());

        System.out.println(buffer.array());
        //
        System.out.println(buffer.hasArray());

        /**
         * 三个参数
         */
        CharBuffer charBuffer = CharBuffer.wrap ("Hello World");
        System.out.println(charBuffer);

        CharBuffer buffers = CharBuffer.allocate (8);
        buffers.position (3).limit (5);
        /**
         * slice后buffer的capacity等于limit - position
         */
        //CharBuffer sliceBuffer = buffers.slice( );

        /**
         * 复制后的capacity不变
         */
        CharBuffer sliceBuffer =  buffers.duplicate();
        sliceBuffer.clear();
        System.out.println(sliceBuffer.isReadOnly());
        sliceBuffer.put('a');
        sliceBuffer.flip();
        System.out.println(sliceBuffer.get());
    }
}
