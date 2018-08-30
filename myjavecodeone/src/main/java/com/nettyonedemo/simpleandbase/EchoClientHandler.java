package com.nettyonedemo.simpleandbase;

import com.cpsdb.base.mapper.JsonMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        System.out.println("Client received :" + in.toString(CharsetUtil.UTF_8));
        //用于判定是否是堆缓冲区
        if (in.hasArray()) {
            byte[] bytes = in.array();
            //计算第一个字节的偏移量
            int offset = in.arrayOffset() + in.readerIndex();
            //获得可读字节数
            int length = in.readableBytes();
            System.out.println("第一个字节偏移量:" + offset + "可读字节数:" + length + "堆缓冲区:" + JsonMapper.buildNonNullMapper().toJson(bytes));
        }
        //判定是否是直接缓冲区
        if (!in.hasArray()) {
            int length = in.readableBytes();
            byte[] bytes = new byte[length];
            //Bytebuf对象in的未读部分（readerIndex开始的部分）复制进bytes字节数组。
            in.getBytes(in.readerIndex(), bytes);
            System.out.println("****************this is the directBuf********************");
            for (byte b : bytes) {
                System.out.println((char) b);
            }
            System.out.println("************************************");
        }
        //复合缓冲区对应的CompositeByteBuf是ByteBuf的子类，这里可以直接进行强转;
//        CompositeByteBuf compositeByteBuf = (CompositeByteBuf) in;

        //以下为对ByteBuf对象的直接访问
        for (int i = 0; i < in.capacity(); i++) {
            byte b = in.getByte(i);
            //注意使用get或set方法不会改变索引,可通过readerIndex(index)或者writerIndex(index)来进行直接改变
            System.out.println((char) b);
        }
        in = in.discardReadBytes();
        System.out.println("回收‘可丢弃字节’之后的ByteBuf:" + in.toString(CharsetUtil.UTF_8) + "可读索引为  " + in.readerIndex());
        if (in.writableBytes() > 4) {
            in.writeByte((byte) 'q');
        }
        System.out.println("写入55之后的结果:" + in.toString(CharsetUtil.UTF_8));

        ByteBuf buf = in.duplicate();
        buf.setByte(0, (byte) 'j');
        System.out.println(buf.getByte(0) == in.getByte(0));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
