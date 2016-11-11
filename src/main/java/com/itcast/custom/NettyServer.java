package com.itcast.custom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by tony on 2016/11/8.
 */
public class NettyServer {
  private static ServerBootstrap bootstrap;

  public static void main(String[] args) throws InterruptedException {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();

    bootstrap = new ServerBootstrap();
    bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
        .handler(new LoggingHandler(LogLevel.INFO))
        .childHandler(new ChannelInitializer<SocketChannel>() {
          protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new ConsoleHandler());
          }
        });
    ChannelFuture ch = bootstrap.bind(8080).sync();
    System.out.println(
        "start server success, you can telnet 127.0.0.1 8082 to send massage for this server");
    ch.channel().closeFuture().sync();
  }
}
