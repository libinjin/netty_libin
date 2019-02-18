package netty.firstExample.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 自己接到请求后，
 * 做处理请求的处理器
 * 需要继承ChannelInitializer<SocketChannel>通道初始化器
 *
 */
public class MyInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //一个管道，相当于多个拦截器
        //addLast是添加到最后
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("httpServerCodec",new HttpServerCodec());

        pipeline.addLast("testhttpServerHandler",new HttpServerHandler());

    }
}
