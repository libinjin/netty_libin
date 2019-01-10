package nettynio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;
        Selector selector = Selector.open();
      /*  //查看selectorProvider.provider()的真正类
        System.out.println(SelectorProvider.provider().getClass());
        System.out.println(sun.nio.ch.DefaultSelectorProvider.create());*/
        //将selector注册到多个端口号上，使得一个selector可以监听多个端口号
        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //实现异步
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress socketAddress = new InetSocketAddress(ports[i]);
            serverSocket.bind(socketAddress);
            //实现通道和选择器的关联关系
            /**
             * 把channel注册到selector上
             * SelectionKey.OP_ACCEPT表示
             * 当客户端向服务端发起连接时，
             * 当服务器端获取连接时，
             * 服务器端拿到真正的连接对象。
             *
             */
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口："+ports[i]);
        }
        while(true){
            //获取到客户端的所发生的event事件集合
            //可以用一个线程处理n多个线程
            int numbers = selector.select();
            System.out.println("numbers:"+numbers);
            //不断的从获取新的事件
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            System.out.println("selectionKeySet:"+selectionKeySet);
            System.out.println("selectionKeySet的个数："+selectionKeySet.size());
            //如果同时又几个连接发送，这几个连接都放到selectionKeySet中
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();

                //isAcceptable表示已经有连接进来了
                //就可以同过selectionKey拿到selector与之关联的channel
                if (selectionKey.isAcceptable()){//是否连接上
                    //向下转型为原来注册时的对象
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    //连接上后，发生一个连接事件，注册到channel上，
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    //并且必须把原来客户端发送的事件移除，否则会报异常
                    iterator.remove();
                    System.out.println("获得客户端连接："+socketChannel);
                }else if(selectionKey.isReadable()){//是否有数据发过来

                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int bytesRead = 0;
                    while(true){
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);
                        if(read <= 0){
                            break;
                        }
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        bytesRead += read;
                    }
                    System.out.println("读到的字节数为："+bytesRead+"来自："+socketChannel);
                    iterator.remove();
                }
            }
        }
    }
}
