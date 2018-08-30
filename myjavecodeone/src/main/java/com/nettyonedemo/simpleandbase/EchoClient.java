package com.nettyonedemo.simpleandbase;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class EchoClient {


    public static void main(String[] args) throws InterruptedException {
        new EchoClient().start();
    }

    public void start() throws InterruptedException {
        final AttributeKey<Integer> id = new AttributeKey<Integer>("ID");
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 8080))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new EchoClientHandler());

                            //不同于上面代码那样的加监听，此处使用的是任务调度的功能;
                            ScheduledFuture scheduledFuture = ch.eventLoop().schedule(() -> {
                                System.out.println("10s已到  开始");
                            }, 10, TimeUnit.SECONDS);
                            boolean flag = false;
                            //得到ScheduledFuture对象然后调用其cancel()方法来避免该Schedule定时任务再次运行;
                            scheduledFuture.cancel(flag);
                        }
                    });
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
