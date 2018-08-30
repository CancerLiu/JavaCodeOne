package com.nettyonedemo.nettyrpcexprient.handle;

import com.nettyonedemo.nettyrpcexprient.server.IMessageHandler;
import com.nettyonedemo.nettyrpcexprient.server.MessageOutput;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 雯波那锲数列计算处理器
 */
public class FibRequestHandler implements IMessageHandler<Integer> {
    private List<Long> fibs = new ArrayList<>();

    {
        fibs.add(1L);
        fibs.add(1L);
    }

    @Override
    public void handle(ChannelHandlerContext ctx, String requestId, Integer n) {
        for (int i = fibs.size(); i < n + 1; i++) {
            long value = fibs.get(i - 2) + fibs.get(i - 1);
            fibs.add(value);
        }
        //输出响应
        ctx.writeAndFlush(new MessageOutput(requestId, "fib_res", fibs.get(n)));
    }
}
