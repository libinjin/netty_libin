package netty.heartBeat.Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //chainResponse，责任链模式，像是一个一个的拦截器
        //IdleStateHandler是空闲检测的一个处理器
        pipeline.addLast(new IdleStateHandler(5,7,7, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
