package nettynio.nioSocket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioClient {
    public static void main(String[] args) throws IOException {
        //
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();

        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("localhost",8899));

        while (true){
            selector.select();
            Set<SelectionKey> set = selector.selectedKeys();
            for (SelectionKey selectionKey : set) {
                if(selectionKey.isConnectable()){
                    final SocketChannel client = (SocketChannel) selectionKey.channel();
                    //如果客户端一直连接服务端
                    if(client.isConnectionPending()){
                        client.finishConnect();
                        final ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                        writeBuffer.put((client.getRemoteAddress()+""+new Date().toString()+"连接成功").getBytes());
                        //客户端从控制台发送消息给服务端,建立只有一个线程的线程池
                        ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                        executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                while (true){
                                    try {
                                        writeBuffer.clear();
                                        System.out.println("请输入：");
                                        InputStreamReader input = new InputStreamReader(System.in);
                                        BufferedReader br = new BufferedReader(input);
                                        String str = br.readLine();
                                        writeBuffer.put(str.getBytes());
                                        writeBuffer.flip();
                                        client.write(writeBuffer);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }    //注册读事件
                    client.register(selector,SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){
                    //读取服务器端发送过来的消息
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                    int count =  client.read(writeBuffer);
                    if(count >0 ){
                        writeBuffer.flip();
                        String receivedMessage = String.valueOf(Charset.forName("utf-8").decode(writeBuffer).array());
                        System.out.println("客户端接收："+client.getRemoteAddress()+"接收的消息："+receivedMessage);
                    }
                }
            }
            set.clear();
        }
    }
}
