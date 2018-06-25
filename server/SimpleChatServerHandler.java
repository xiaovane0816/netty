package com.vane.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SimpleChatServerHandler extends SimpleChannelInboundHandler {
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx){
        Channel incoming = ctx.channel();

        channels.writeAndFlush("[Server] - " + incoming.remoteAddress() + "加入！ \n");
        channels.add(incoming);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        Channel inComing = ctx.channel();

        channels.writeAndFlush("[Server] - " + inComing.remoteAddress() + "离开！\n");

        //ChannelGroup会自动删掉离开的channel，所以不用调用 channels.remove(inComing.channel())
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        Channel inComing = channelHandlerContext.channel();
        for(Channel channel:channels) {
            if(channel != inComing){
                channel.writeAndFlush("[" + inComing.remoteAddress() + "] say: " + o + "\n");
            } else {
                channel.writeAndFlush("[you] say: " + o + "\n");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();
        System.out.println("chat: " + channel.remoteAddress() + "在线！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        System.out.println("chat: " + channel.remoteAddress() + "离线！");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
