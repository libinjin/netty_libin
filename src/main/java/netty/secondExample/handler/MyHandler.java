package netty.secondExample.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.net.SocketAddress;
import java.util.UUID;

public class MyHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        SocketAddress address = ctx.channel().remoteAddress();
        System.out.println(address+"得到消息："+msg);
        ctx.channel().writeAndFlush("来自server端"+ UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
