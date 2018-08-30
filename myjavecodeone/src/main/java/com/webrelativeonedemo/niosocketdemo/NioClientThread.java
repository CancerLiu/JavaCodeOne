package com.webrelativeonedemo.niosocketdemo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NioClientThread extends Thread {

    private Selector selector;

    private Charset charset;

    public NioClientThread(Selector selector, Charset charset) {
        this.selector = selector;
        this.charset = charset;
    }

    @Override
    public void run() {
        try {
            while (selector.select() > 0) {
                for (SelectionKey sk : selector.selectedKeys()) {
                    selector.selectedKeys().remove(sk);
                    if (sk.isReadable()) {
                        SocketChannel sc = (SocketChannel) sk.channel();
                        ByteBuffer buff = ByteBuffer.allocate(1024);
                        String content = "";
                        while (sc.read(buff) > 0) {
                            sc.read(buff);
                            buff.flip();
                            content += charset.decode(buff);
                        }
                        System.out.println("聊天信息：" + content);
                        sk.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
