package com.max.netty;

import cn.hutool.core.date.DateUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.ChannelMatchers;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

/**
 * @author max
 * @date 2023年03月05日 22:16
 */
//@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        group.add(channel);
        group.writeAndFlush(DateUtil.now() + " [客户端] " + channel.remoteAddress() + " 加入群聊！");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(DateUtil.now() + " [客户端] " + channel.remoteAddress() + " 上线！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(DateUtil.now() + " [客户端] " + channel.remoteAddress() + " 下线！");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(group.size());
        channel.writeAndFlush(DateUtil.now() + " [客户端] " + channel.remoteAddress() + " 离开群聊！");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
//        for (Channel ch : group) {
//            if (ch != channel) {
//
//            }else {
//                group.writeAndFlush(DateUtil.now() + " [自己]说：" + s);
//            }
//        }
        group.writeAndFlush(DateUtil.now() + " [客户端] " + channel.remoteAddress() + " 说：" + s, ChannelMatchers.isNot(channel));
        group.writeAndFlush(DateUtil.now() + " [自己]说：" + s, ChannelMatchers.is(channel));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();

    }

    /**
     * 处理事件
     * @author max
     * @date 2023/3/6 23:10
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent ise = (IdleStateEvent) evt;
            IdleState state = ise.state();
            switch (state) {
                case READER_IDLE:
                    ;
                case WRITER_IDLE:
                    ;
                case ALL_IDLE:
                    ;
            }
            System.out.println(DateUtil.now() + " " + ctx.channel().remoteAddress() + " " + state + " 处理事件ing----");
        }
    }
}
