package com.webrelativeonedemo.aiosocketdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AioServer {
    static final int PORT = 30000;
    final static String UTF_8 = "utf-8";

    static List<AsynchronousSocketChannel> channelList = new ArrayList<>();

    public void startListen() throws IOException {
        //创建一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(20);

        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);

        //以指定线程池创建对象
        AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open(channelGroup).bind(new InetSocketAddress(PORT));

//        serverChannel.accept(null,new AcceptHandler);
    }
}
