package com.itcast.custom.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by tony on 2016/11/8.
 */
public class HelloServer {
    private static final int port = 8080;

    public static void main(String[] args) throws InterruptedException {
        // EventLoopGroup用于管理channel，可以理解为
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            // 绑定服务端处理的handler
            bootstrap.childHandler(new HelloServerInitializer());
            ChannelFuture ch = bootstrap.bind(port).sync();
            ch.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
