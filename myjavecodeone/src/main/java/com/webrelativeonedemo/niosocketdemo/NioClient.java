package com.webrelativeonedemo.niosocketdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class NioClient {

    private Selector selector = null;

    private final static int PORT = 30000;

    private Charset charset = Charset.forName("UTF-8");

    private SocketChannel sc = null;

    public void init() throws IOException {
        selector = Selector.open();
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", PORT);

        sc = SocketChannel.open(isa);

        sc.configureBlocking(false);

        sc.register(selector, SelectionKey.OP_READ);

        //此处开一个线程，用于打印从服务器端返回的数据
        Executors.newCachedThreadPool().execute(new NioClientThread(selector, charset));

        //主线程用于接收键盘输入的信息
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            sc.write(charset.encode(line));
        }
    }

    public static void main(String[] args) throws IOException {
        new NioClient().init();
    }
}

