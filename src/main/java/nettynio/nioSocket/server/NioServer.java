package nettynio.nioSocket.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {

    //维护客户端的连接信息
    private static Map<String,SocketChannel> clientMap = new HashMap();
    public static void main(String[] args) throws IOException {
        //连接服务器端后，服务器端保存一个MAC信息，一个uuid
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //一定要把serverSocketChannel设置成异步的
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        //把channel注册
        System.out.println("服务端开启");
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        //注册完之后，就开始相应的处理，在服务端就是一个死循环
        while(true){
            try {
                selector.select();
                //SelectionKey,不仅可以通过它获取到事件的集合，而且可以根据它得到相应的事件
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                SocketChannel client;

                for (SelectionKey selectionKey: selectionKeySet) {
                    if(selectionKey.isAcceptable()){
                        //由于判断isAcceptable()，所以强转得到的是上面的,之前注册的OP_ACCEPT事件的ServerSocketChannel
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        client = server.accept();//等待连接
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_READ);
                        /**
                         * 将客户端的身份信息记录到服务端
                         * 这样服务端才能实现分发
                         * 用一个map对象
                         * map.put("uuid","连接对象")
                         */
                        String key = UUID.randomUUID().toString();
                        System.out.println("uuid:"+key);
                        //客户端的注册
                        clientMap.put(key,client);
                    }else if (selectionKey.isReadable()){
                        /**
                         * 之前注册的是什么对象，强转的就是什么对象
                         */
                        client = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(512);
                        int count = client.read(readBuffer);
                        if (count<=0) break;
                        readBuffer.flip();
                        //字符解码为UTF-8格式
                        Charset charset = Charset.forName("UTF-8");
                        String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
                        String sendKey = "";
                        for (Map.Entry<String,SocketChannel> entry : clientMap.entrySet()) {
                            if (client == entry.getValue()) {
                                sendKey = entry.getKey();
                                break;
                            }
                        }
                        String member = receivedMessage.split("@")[0];
                        System.out.println("服务器接受"+sendKey+"消息："+receivedMessage);
                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                        writeBuffer.put((sendKey+":"+receivedMessage).getBytes());
                        writeBuffer.flip();
                        clientMap.get(member).write(writeBuffer);

                 /*       for (Map.Entry<String,SocketChannel> entry : clientMap.entrySet()) {
                            SocketChannel value = entry.getValue();
                            //把数据放到buffer,然后读取
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((sendKey+":"+receivedMessage).getBytes());
                            writeBuffer.flip();
                            value.write(writeBuffer);
                        }*/
                    }
                }
                //全部处理完后，清空集合
                selectionKeySet.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
