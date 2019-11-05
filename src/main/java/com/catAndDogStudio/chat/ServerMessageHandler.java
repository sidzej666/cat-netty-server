package com.catAndDogStudio.chat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;
import java.util.Scanner;

public class ServerMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client enters chat room");

        Message message = new Message("SERVER", new Date(), "Hello, server here");
        ByteBuf byteBuf = ctx.alloc().buffer();
        String content = message.encodeMsg();
        byteBuf.writeBytes(content.getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg1) throws Exception {
        try {
            Message msg = (Message) msg1;
            System.out.println(msg.toString());
            Scanner scanner = new Scanner(System.in);
            System.out.print("SERVER, please input msg: ");
            String reply = scanner.nextLine();

            Message message = new Message("SERVER", new Date(), reply);
            ctx.writeAndFlush(message);
        } finally {
            ReferenceCountUtil.release(msg1);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
