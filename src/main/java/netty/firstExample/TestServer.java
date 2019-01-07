package netty.firstExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.firstExample.handler.MyServerHandler;

/**
 * 模拟客户端发生消息
 * 服务器返回一个消息
 */
public class TestServer {
    public static void main(String[] args) {
        //事件循环组,就是一个类似于一个死循环
        /**
         * bossGroup的EventLoopGroup就是从客户端接收到连接并直接
         * 转发给worker组，
         * workerGroup是真正处理业务并返回给客户端
         * netty推荐使用两个线程组来模拟
         */
        EventLoopGroup bossGroup  = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            System.out.println("启动服务器");
            /**
             * ServerBootstrap是一个启动服务端的类
             * extends了服务管道ServerChannel
             * 封装了，使得可以轻松的启动服务端的代码
             */
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            /*
             * 用到了NioServerSocketChannel这个管道
             * .childHandler是子处理器，可以是我们自己处理
             * 业务的处理器，当请求到来时，真正所处理请求的处理器
             */
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerHandler());

            ChannelFuture channelFuture = serverBootstrap.bind(8893).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
