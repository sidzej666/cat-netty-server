package com.catAndDogStudio.cat.client;

import com.catAndDogStudio.catService.CatServiceResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class CatClientHandler extends SimpleChannelInboundHandler<CatServiceResponse.Response> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, CatServiceResponse.Response msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
