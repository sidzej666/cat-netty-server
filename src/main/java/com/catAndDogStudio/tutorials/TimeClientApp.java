package com.catAndDogStudio.tutorials;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClientApp {
    private int port;

    public TimeClientApp(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        NioEventLoopGroup workingGroup = new NioEventLoopGroup();

        try {
            Bootstrap clientBootstrap = new Bootstrap();
            clientBootstrap.group(workingGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = clientBootstrap.connect("localhost", 8000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workingGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new TimeClientApp(8000).run();
    }
}