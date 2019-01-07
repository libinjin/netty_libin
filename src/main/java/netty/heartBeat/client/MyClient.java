package netty.heartBeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
            Channel channel = bootstrap.connect("localhost",8819).sync().channel();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for (; ;){
               channel.writeAndFlush(br.readLine()+"\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
