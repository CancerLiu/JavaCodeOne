package com.nettyonedemo.nettyrpcexprient.server;

import com.cpsdb.base.mapper.JsonMapper;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 服务器端响应浏览器编码类
 */
public class MessageEncoder extends MessageToMessageEncoder<MessageOutput> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MessageOutput msg, List<Object> out) throws Exception {
        //池化技术，此处返回一个直接内存存储的ByteBuf实例，并往里面写东西，注意顺序;
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        writeStr(buf, msg.getRequestId());
        writeStr(buf, msg.getType());
        writeStr(buf, JsonMapper.buildNonNullMapper().toJson(msg.getPayload()));
        out.add(buf);
    }

    private void writeStr(ByteBuf buf, String s) {
        //注意此处写入的格式(客户端也是这样写入的，这样的写法其实就是客户端与服务器端的一种编码与解码的约定，往大了说就是一种协议);
        //此处的“协议”就是写入字节数组的顺序和每个实际内容之前，记录该内容的长度;
        buf.writeInt(s.length());
        buf.writeBytes(s.getBytes(Charsets.UTF_8));
    }
}
