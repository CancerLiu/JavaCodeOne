package com.iostreamonedemo.niostream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ChannelTest {

    /**
     * 少量数据(一次可取完)时候使用Channel
     * 通过NIO读取数据并转存到新的文件中。然后在程序内部解析数据并读取打印
     *
     * @throws IOException
     */
    private void singleChannel() throws IOException {
        File inf = new File("E:/JavaCodeDocument/stream/channelInput.txt");
        File outf = new File("E:/JavaCodeDocument/stream/channelOutput.txt");
        //用于输入和输出的Channel
        FileChannel inChannel = new FileInputStream(inf).getChannel();
        FileChannel outChannel = new FileOutputStream(outf).getChannel();

        //将输入的Channel映射为Buffer
        MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inf.length());
        //然后将该Buffer数据通过输出Channel输出到目标文件
        outChannel.write(buffer);

        //此时来打印内容(即buffer中的内容)
        //调用clear()方法用于复原limit和position的位置,之所以复原两者的位置是为了解码需要？解码也需要Buffer的指针？
        buffer.clear();
        //创建解码器相关
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder = charset.newDecoder();
        //使用解码器将ByteBuffer转换为CharBuffer
        CharBuffer charBuffer = decoder.decode(buffer);
        //CharBuffer的toString()方法可以获取对应的字符串
        System.out.println(charBuffer);
    }

    /**
     * 大量数据(需要多次取)时候使用Channel
     */
    private void muchChannel() throws IOException {
        FileInputStream fis = new FileInputStream(new File("E:/JavaCode/stream/mushChannel.txt"));

        FileChannel fileChannel = fis.getChannel();

        //定义一个ByteBuffer对象，用于重复取数据
        ByteBuffer buff = ByteBuffer.allocate(256);

        //将FileChannel中的数据放入ByteBuffer中
        while (fileChannel.read(buff) != -1) {
            //锁定Buffer空白区用于读取
            buff.flip();
            //创建Charset对象
            Charset charset = Charset.forName("UTF-8");
            //创建解码器(CharsetDecoder)对象
            CharsetDecoder decoder = charset.newDecoder();
            //将ByteBuffer的内容转码
            CharBuffer cbuff = decoder.decode(buff);
            System.out.println(cbuff);
            //将Buffer初始化，为下一次读取数据做准备
            buff.clear();
        }
    }

    public static void main(String[] args) throws IOException {
        ChannelTest channelTest = new ChannelTest();
//        channelTest.singleChannel();
        channelTest.muchChannel();
    }
}
