package com.max.netty.codec.bytemsg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author max
 * @date 2023年03月05日 23:15
 */
public class ClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        long l = 13645645345L;
        System.out.println("发送消息给服务端:" + l);
        channel.writeAndFlush(l);
//        channel.writeAndFlush(Unpooled.copiedBuffer("aaacccdddkqwertt", StandardCharsets.UTF_8));

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long s) throws Exception {
        System.out.println("收到服务端消息：" + s);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
