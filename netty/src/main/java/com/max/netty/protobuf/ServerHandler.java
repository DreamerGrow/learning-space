package com.max.netty.protobuf;

import cn.hutool.core.date.DateUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatchers;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author max
 * @date 2023年03月05日 22:16
 */
public class ServerHandler extends SimpleChannelInboundHandler<UserPo.User> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, UserPo.User s) throws Exception {
        System.out.println("id:" + s.getId() + " name:" + s.getName());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();

    }
}
