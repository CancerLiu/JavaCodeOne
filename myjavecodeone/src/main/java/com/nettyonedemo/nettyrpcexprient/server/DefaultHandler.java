package com.nettyonedemo.nettyrpcexprient.server;

import io.netty.channel.ChannelHandlerContext;

public class DefaultHandler implements IMessageHandler<MessageInput> {
    @Override
    public void handle(ChannelHandlerContext ctx, String requestId, MessageInput message) {
        System.out.println("消息类型未定义" + message.getType());
    }
}
