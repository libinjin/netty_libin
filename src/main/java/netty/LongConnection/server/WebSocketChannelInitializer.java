package netty.LongConnection.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("",new HttpServerCodec());
        //以块的方式去写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //采取分块和分段的形式，以字节的形式来聚合最大的内容长度时handleOversizedMessage就会被调用
        pipeline.addLast(new HttpObjectAggregator(8192));
        // /ws就是websocket协议的  ws://localhost:8080/ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/query"));
        pipeline.addLast(new WebSocketFrameHandler());
    }
}
