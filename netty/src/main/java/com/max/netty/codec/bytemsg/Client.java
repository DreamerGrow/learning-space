package com.max.netty.codec.bytemsg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.util.Scanner;

/**
 * @author max
 * @date 2023年03月05日 22:33
 */
public class Client {

    private final String host;

    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new Client("127.0.0.1", 8011).run();
    }

    private void run() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ClientHandler handler = new ClientHandler();
            Bootstrap b = new Bootstrap().group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new LongToByteEncoder())
                                    .addLast(new ByteToLongDecoder2())
                                    .addLast(handler);

                        }
                    });
            ChannelFuture cf = b.connect(host, port).sync();
            Channel ch = cf.channel();
            System.out.println("===" + ch.remoteAddress() + "===");

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                ch.writeAndFlush(msg);
            }

            ch.closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
