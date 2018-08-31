package com.iostreamonedemo.niostream;

import java.nio.CharBuffer;

public class BufferTest {
    public static void main(String[] args) {
        //创建buff
        CharBuffer buff = CharBuffer.allocate(8);
        System.out.println("Capacity:" + buff.capacity());
        System.out.println("Limit:" + buff.limit());
        System.out.println("Position:" + buff.position());

        //放入元素——Java中单引号为字符
        buff.put('a');
        buff.put('b');
        buff.put('c');
        System.out.println("加入三个元素后，position = " + buff.position());

        //调用flip()方法锁死buff，位置后的读数据做准备
        buff.flip();
        System.out.println("执行flip()后，limit = " + buff.limit());
        System.out.println("执行flip()后，position = " + buff.position());

        //取出第一个元素
        System.out.println("第一个元素(position=0):" + buff.get());
        System.out.println("取出一个元素后，position = " + buff.position());

        //调用clear()方法，开启锁死的buff，准备读入数据
        buff.clear();
        System.out.println("执行clear()后，limit=" + buff.limit());
        System.out.println("执行clear()后，position =" + buff.position());

        System.out.println("另外，执行clear()方法后，缓冲区内容并没有被清楚:第三个元素为:" + buff.get(2));
        System.out.println("执行绝对读取后(position位置不会改变)，position = " + buff.position());
    }

}
