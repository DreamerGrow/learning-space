package com.max.netty.protobuf;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author max
 * @date 2023年03月05日 23:15
 */
public class ClientHandler extends SimpleChannelInboundHandler<UserPo.User> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channel.writeAndFlush(UserPo.User.newBuilder().setId(1).setName("你好").build());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UserPo.User s) throws Exception {
        System.out.println(s);
    }
}
