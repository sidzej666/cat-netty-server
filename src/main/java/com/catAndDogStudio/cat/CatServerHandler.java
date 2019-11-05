package com.catAndDogStudio.cat;

import com.catAndDogStudio.cat.catProcessors.BossCat;
import com.catAndDogStudio.cat.catProcessors.MauCatProcessor;
import com.catAndDogStudio.catService.CatServiceRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.RequiredArgsConstructor;

public class CatServerHandler extends SimpleChannelInboundHandler<CatServiceRequest.Request> {

    private final ChannelGroup allCats;
    private final BossCat boosCat;

    public CatServerHandler(final BossCat bossCat, final ChannelGroup channelGroup) {
        this.allCats = channelGroup;
        this.boosCat = bossCat;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        final Channel newCat = ctx.channel();
        allCats.add(newCat);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        final Channel leavingCat = ctx.channel();
        allCats.remove(leavingCat);
        boosCat.catHadLeft(ctx.channel().id().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, CatServiceRequest.Request msg) throws Exception {
        System.out.println("message received: " + msg);
        boosCat.handleRequest(ctx.channel(), msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
