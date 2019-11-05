package com.catAndDogStudio.cat;

import com.catAndDogStudio.cat.catProcessors.BossCat;
import com.catAndDogStudio.cat.catProcessors.GiveFoodCatProcessor;
import com.catAndDogStudio.cat.catProcessors.MauCatProcessor;
import com.catAndDogStudio.cat.data.CatsData;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

public class CatServer {

    private final int port;

    public static void main(String[] arg) throws InterruptedException {
        new CatServer(8000).run();
    }

    public CatServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        final ChannelGroup allCats = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        final CatsData catsData = new CatsData();
        final BossCat bossCat = new BossCat(new GiveFoodCatProcessor(), new MauCatProcessor(allCats, catsData), catsData);

        try {
            final ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new CatServerInitializer(bossCat, allCats))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            final ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
