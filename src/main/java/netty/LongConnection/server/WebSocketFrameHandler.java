package netty.LongConnection.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Date;

/**
 * TextWebSocketFrame是专门处理文本阵的类
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("收到消息："+msg.text());
        //将服务器端构造的
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间："+new Date()));
    }

    //刚刚连接上要做的事情
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded:"+ctx.channel().id().asLongText());
    }

    //关闭连接时会做的
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved:"+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
