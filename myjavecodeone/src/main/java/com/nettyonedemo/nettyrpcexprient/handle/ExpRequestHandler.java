package com.nettyonedemo.nettyrpcexprient.handle;

import com.nettyonedemo.nettyrpcexprient.common.ExpRequest;
import com.nettyonedemo.nettyrpcexprient.common.ExpResponse;
import com.nettyonedemo.nettyrpcexprient.server.IMessageHandler;
import com.nettyonedemo.nettyrpcexprient.server.MessageOutput;
import io.netty.channel.ChannelHandlerContext;

/**
 * 指数计算处理器
 */
public class ExpRequestHandler implements IMessageHandler<ExpRequest> {

    @Override
    public void handle(ChannelHandlerContext ctx, String requestId, ExpRequest message) {
        int base = message.getBase();
        int exp = message.getExp();
        long start = System.nanoTime();
        long res = 1;

        for (int i = 0; i < exp; i++) {
            res *= base;
        }
        long cost = System.nanoTime() - start;
        ctx.writeAndFlush(new MessageOutput(requestId, "exp_res", new ExpResponse(res, cost)));
    }

}
