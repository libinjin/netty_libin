package netty.manyTel.Server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;

public class TelHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel channel = ctx.channel();
        Iterator iterator = channelGroup.iterator();
        while (iterator.hasNext()){
            Channel nowchannel = (Channel) iterator.next();
            if(channel!= nowchannel){
                nowchannel.writeAndFlush(channel.remoteAddress()+"发送消息："+msg+"\n");
            }else{
                nowchannel.writeAndFlush("自己："+msg+"\n");
            }
        }
    }

    //当客户端和服务端建立连接后触发的事件叫做handlerAdded
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //channelGroup.writeAndFlush("客户端："+channel.remoteAddress()+"加入\n");
        //实现一个广播告诉其他客户端，服务端要保存所有已经建立好的连接
        channelGroup.add(channel);//
        System.out.println("客户端："+channel.remoteAddress()+"加入");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
         Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"注册进来");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //channelGroup.writeAndFlush("客户端-"+channel.remoteAddress()+"离开\n");
        //System.out.println(channelGroup.size());
        System.out.println("客户端-"+channel.remoteAddress()+"离开");
    }
    //监听客户端连接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"上线");
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        /*channelGroup.writeAndFlush("服务器-"+channel.remoteAddress()+"下线");*/
        System.out.println(channel.remoteAddress()+"下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
