package netty.secondExample.Client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
    public static void main(String[] args) {
        /**
         * 连接服务端
         */
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap  = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientInitlizer());
            System.out.println("连接服务器端");
            ChannelFuture channelFuture = bootstrap.connect("localhost",8806).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
