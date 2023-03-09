package com.max.netty.ws.demo;

import cn.hutool.core.date.DateUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * @author max
 * @date 2023年03月07日 20:41
 */
public class WSFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) throws Exception {
        TextWebSocketFrame frame = (TextWebSocketFrame) webSocketFrame;
        System.out.println("收到消息：" + frame.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("当前时间：" + DateUtil.now() + " " + frame.text()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id().asShortText());
        System.out.println(ctx.channel().id().asLongText() + "连接成功");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id().asLongText() + "断开连接");
    }
}
