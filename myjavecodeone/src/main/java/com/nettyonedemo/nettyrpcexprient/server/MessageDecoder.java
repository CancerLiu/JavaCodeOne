package com.nettyonedemo.nettyrpcexprient.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * 服务器端负责对传入的数据进行验证，然后封装到字节数组中，指定编码后返回
 */
public class MessageDecoder extends ReplayingDecoder<MessageInput> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //注意顺序,需要是和服务器端输入的顺序一样,否则解析出的数据会出错;
        String requestId = readStr(in);
        String type = readStr(in);
        String content = readStr(in);

        //之后将解析出的数据封装为对象，传入链中
        out.add(new MessageInput(requestId, type, content));
    }

    private String readStr(ByteBuf in) {
        //readInt()方法，返回当前readerIndex处的int值，并将readerIndex增加4——即一次读4位
        //能够这样读的前提是，客户端也是这样4位4位的组装数据的;
        int len = in.readInt();
        if (len < 0 || len > (1 << 20)) {
            throw new DecoderException("信息内容过长" + len);
        }
        byte[] bytes = new byte[len];
        //将读出的字节读入字节数组中，并以String方式返回
        in.readBytes(bytes);
        return new String(bytes, CharsetUtil.UTF_8);
    }
}
