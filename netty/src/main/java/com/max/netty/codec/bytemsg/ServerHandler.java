package com.max.netty.codec.bytemsg;

import com.max.netty.protobuf.UserPo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author max
 * @date 2023年03月05日 22:16
 */
public class ServerHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long l) throws Exception {
        System.out.println("收到客户端的消息：" + l);

        long l1 = 1359489579235L;
        System.out.println("发送消息给客户端：" + l1);
        channelHandlerContext.channel().writeAndFlush(l1);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
