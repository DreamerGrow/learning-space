package com.max.netty.codec.bytemsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * ReplayingDecoder 不需要对字节数组长度或类型判断，可以直接read后放到list中
 * 不过在网络缓慢或数据量较大时，处理速度可能不如ByteToMessageDecoder
 * @author max
 * @date 2023/3/9 18:29
 */
public class ByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("ByteToLongDecoder2 working");
        list.add(byteBuf.readLong());
    }
}
