package netty.firstExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.firstExample.handler.MyInitializer;

/**
 * netty的启动，模板型代码
 *
 * 模拟客户端发生消息
 * 服务器返回一个消息
 */
public class TestServer {
    public static void main(String[] args) {
        //事件循环组,就是一个类似于一个死循环
        /**
         * bossGroup的EventLoopGroup就是从客户端接收到连接并直接
         * 转发给worker组，只用于获取连接，其它什么事情都不会做
         *
         * workerGroup是真正处理业务并返回给客户端
         * netty推荐使用两个线程组来模拟
         *
         * bossGroup也可以叫parentGroup
         * workGroup也可以叫childGroup
         * 都是一个死循环，等待客户端的连接
         */
        /**
         * 如果没有提供线程数时，默认
         *
         *  DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
         *                 "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
         *根据电脑核数来定，Inter有超核数是实际核数的2倍
         */
        //此时netty并没有启动，只是做了一些赋值工作
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
             *
             * .handler()是针对bossGroup来说的
             * .childHandler()是针对workGroup来说的
             */
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyInitializer());

            /**
             * sync()设置异步的处理方式，这样可以判断异步的操作是不是执行完了
             * 没有等待异步方法完成，就立即返回了一个future
             */
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
