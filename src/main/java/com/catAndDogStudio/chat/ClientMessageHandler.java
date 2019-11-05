package com.catAndDogStudio.chat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

public class ClientMessageHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        try {
            System.out.println(msg.toString());
            Scanner scanner = new Scanner(System.in);
            System.out.print("server client, please input msg: ");
            String reply = scanner.nextLine();

            Message message = new Message("CLIENT", new Date(), reply);
            ByteBuf buffer = ctx.alloc().buffer();
            String content = message.getUsername() + "~" + msg.formatDateTime() + "~" + message.getMsg();
            buffer.writeBytes(content.getBytes(StandardCharsets.UTF_8));
            ctx.writeAndFlush(buffer);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}