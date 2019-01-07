package netty.firstExample.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler
 * inbound是在网络编程中常见的术语，inbound是对进来请求的处理
 * outbound是出去，是对返回的处理
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端发送过来的信息，并且响应给客户端
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println("远程地址："+ctx.channel().remoteAddress());
        if(msg instanceof HttpRequest){
            HttpRequest request = (HttpRequest) msg;
            System.out.println("这是一个Http请求");
            /**
             * 还可以得到另外一些请求过来的的详细信息
             */
            String requestType = request.getMethod().name();
            System.out.println("请求方法名是:"+requestType);
            URI uri = new URI(request.uri());
            System.out.println("请求过来的uri是："+uri);
            if("/favicon.ico".equals(request.uri())){
                System.out.println("这个是favicon.ico");
                return;
            }
        }
        //向客户端返回的消息
        ByteBuf content = Unpooled.copiedBuffer("你好啊！", CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
        //返回给客户端
        //服务端可以
        ctx.writeAndFlush(response);
    }
    //覆盖活动时候的方法，当请求这里面的方法时，就会触发响应的回调
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("channel active的回调");
        super.channelActive(ctx);
    }
    //覆盖被注册时的方法
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("被注册时调用");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added的回调");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel incative的回调");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered的回调");
        super.channelUnregistered(ctx);
    }
}
