package com.nettyonedemo.nettyrpcexprient.server;

import io.netty.channel.ChannelHandlerContext;

/**
 * 服务器端消息处理器接口，每个自定义接口必须实现该接口的handler方法
 *
 * @param <T> 消息类型
 */
public interface IMessageHandler<T> {

    /**
     * 此处的Handler实际上应该是和ChannelHandler同一个等级的处理器，所以其中也可以使用ctx.writeAndFlush()来传递给下一个ChannelHandler的方法;
     *
     * @param ctx       ChannelHandlerContext代表了ChannelHandler和ChannelPipeline直接的关系，所以在哪里都可以取;
     * @param requestId 请求对象的id
     * @param message   请求的具体消息对象
     */
    void handle(ChannelHandlerContext ctx, String requestId, T message);
}
