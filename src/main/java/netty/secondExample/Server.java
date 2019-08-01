package netty.secondExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.secondExample.init.MyInitializer;


public class Server {
    public static void main(String[] args) {
        /**
         * 事件循环组，不断等待事件发生的组
         * EventLoopGroup仅完成了参数的赋值
         *
         * bossGroup用于转发
         *
         * workGroup是真正的完成处理的类
         * EventLoopGroup底层就是一个死循环
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //group()方法就是完成了成员变量的赋值
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new MyInitializer());
        try {
            System.out.println("启动服务器");
            ChannelFuture future = serverBootstrap.bind(8806).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
