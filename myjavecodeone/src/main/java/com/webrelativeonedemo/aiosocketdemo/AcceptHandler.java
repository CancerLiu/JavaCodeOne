package com.webrelativeonedemo.aiosocketdemo;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {

    private AsynchronousServerSocketChannel serverChannel;

    public AcceptHandler(AsynchronousServerSocketChannel serverSocketChannel) {
        this.serverChannel = serverSocketChannel;
    }

    //定义一个ByteBuff准备读取数据
    ByteBuffer buff = ByteBuffer.allocate(1024);

    @Override
    public void completed(AsynchronousSocketChannel result, Object attachment) {
        //记录新连接进来的Channel
        AioServer.channelList.add(result);
        //准备接收客户端的下一次连接
        serverChannel.accept(null, this);
        result.read(buff, null, new CompletionHandler<Integer, Object>() {

            @Override
            public void completed(Integer in, Object attachment) {
                buff.flip();
                String content = StandardCharsets.UTF_8.decode(buff).toString();
                //遍历每个Channel，将收到的信息写入各Channel中
                for (AsynchronousSocketChannel c : AioServer.channelList) {
                    try {
                        c.write(ByteBuffer.wrap(content.getBytes(AioServer.UTF_8))).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                buff.clear();
                result.read(buff, null, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("读取数据失败：" + exc);
                AioServer.channelList.remove(result);
            }
        });
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        System.out.println("连接失败" + exc);
    }
}
