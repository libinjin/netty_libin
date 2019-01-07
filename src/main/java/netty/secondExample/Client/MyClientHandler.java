package netty.secondExample.Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;
import java.util.Date;
import java.util.Locale;

public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 接收到服务器发送过来的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        SocketAddress address = ctx.channel().remoteAddress();
        System.out.println("服务器地址："+address+",发送的消息为："+msg);
        ctx.channel().writeAndFlush("form 客户端："+ new Date());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("来自客户端的问候");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
