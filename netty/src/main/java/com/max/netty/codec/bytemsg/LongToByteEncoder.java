package com.max.netty.codec.bytemsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class LongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long o, ByteBuf byteBuf) throws Exception {
        System.out.println("LongToByteEncoder working, send:" + o);
        byteBuf.writeLong(o);
    }
}
