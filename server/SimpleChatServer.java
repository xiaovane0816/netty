package com.vane.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleChatServer {
    private int port;
    public SimpleChatServer(int port) {
        this.port = port;
    }
    public void run(){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(eventLoopGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleHttpServerInitializer());
            System.out.println("SimpleChatServer 启动了");
            ChannelFuture channelFuture = bootstrap.bind(port).sync();


            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            System.out.println("SimpleChat 关闭了");
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        new SimpleChatServer(port).run();
    }
}
