package com.max.netty.ws.demo;

import com.max.netty.chat.ChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author max
 * @date 2023年03月06日 23:46
 */
public class WSServer {

    private int port;

    private final String path = "/websocket";

    public WSServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new WSServer(7777).run();
    }


    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline
                                    // http编解码器，是HttpRequestDecoder和HttpResponseEncoder的组合
                                    .addLast(new HttpServerCodec())
                                    // 由于http请求数据是分块传输的，该聚合器可以将http请求数据合并在一起
                                    .addLast(new HttpObjectAggregator(65536))
                                    .addLast(new ChunkedWriteHandler())

                                    .addLast(new WebSocketServerProtocolHandler(path))

                                    .addLast(new WSFrameHandler())
                            ;

                        }
                    });

            ChannelFuture cf = b.bind(port).sync();
            System.out.println("====服务器启动====");
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
