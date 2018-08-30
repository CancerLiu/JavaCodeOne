package com.webrelativeonedemo.niosocketdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

public class NioServer {

    private final static Logger logger = LoggerFactory.getLogger(NioServer.class);

    private Selector selector;

    static final int PORT = 30000;

    private Charset charset = Charset.forName("UTF-8");

    public void init() throws IOException {
        selector = Selector.open();

        ServerSocketChannel server = ServerSocketChannel.open();

        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", PORT);

        //将ServerSocketChannel绑定到指定的IP+端口地址
        server.bind(isa);
        //此处将该ServerSocketChannel设置为非阻塞模式。需要注册到Selector中的Channel都需要设置为非阻塞模式的;
        server.configureBlocking(false);
        //进行注册
        server.register(selector, SelectionKey.OP_ACCEPT);
        logger.info("all keys " + selector.keys().size() + " one");
        logger.info("selected keys " + selector.selectedKeys().size() + " one");
        //使用selector的select方法，查看是否有准备好的对象
        while (selector.select() > 0) {

            for (SelectionKey sk : selector.selectedKeys()) {
                //从selector上的已选择key集中删除正在处理的SelectionKey
                selector.selectedKeys().remove(sk);
                //如果是连接请求
                if (sk.isAcceptable()) {
                    SocketChannel sc = server.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    //将sk对应的Channel设置成准备接收其他请求
                    sk.interestOps(SelectionKey.OP_ACCEPT);
                    logger.info("all keys " + selector.keys().size() + " two");
                    logger.info("selected keys " + selector.selectedKeys().size() + " two");
                }
                if (sk.isReadable()) {
                    SocketChannel sc = (SocketChannel) sk.channel();
                    //定义准备执行读取数据的缓冲对象
                    ByteBuffer buff = ByteBuffer.allocate(1024);
                    String content = "";
                    logger.info("all keys " + selector.keys().size() + " three");
                    logger.info("selected keys " + selector.selectedKeys().size() + " three");
                    //开始读取数据
                    try {
                        while (sc.read(buff) > 0) {
                            buff.flip();
                            content += charset.decode(buff);
                        }
                        System.out.println("读取到的数据：" + content);
                        sk.interestOps(SelectionKey.OP_READ);
                    } catch (IOException e) {
                        sk.cancel();
                        if (sk.channel() != null) {
                            sk.channel().close();
                        }
                    }
                    if (content.length() > 0) {
                        for (SelectionKey key : selector.keys()) {
                            Channel targetChannel = key.channel();
                            if (targetChannel instanceof SocketChannel) {
                                SocketChannel dest = (SocketChannel) targetChannel;
                                dest.write(charset.encode(content));
                            }
                        }
                    }
                }
            }
        }
        logger.info("all keys " + selector.keys().size() + " four");
        logger.info("selected keys " + selector.selectedKeys().size() + " four");
    }

    public static void main(String[] args) throws IOException {
        new NioServer().init();
    }
}
