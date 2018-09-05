package com.webrelativeonedemo.niosocketdemo;

import com.cpsdb.base.mapper.JsonMapper;
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
        //此处将该ServerSocketChannel设置为非阻塞模式。需要注册到Selector中的Channel都需要设置为非阻塞模式的，统称SelectableChannel;
        server.configureBlocking(false);
        //进行注册
        server.register(selector, SelectionKey.OP_ACCEPT);
        logger.info("all keys number is" + selector.keys().size() + "  one");
        logger.info("all keys +" + JsonMapper.buildNonNullMapper().toJson(selector.keys()));
        logger.info("selected keys number is" + selector.selectedKeys().size() + " one");
        logger.info("selected keys" + JsonMapper.buildNonNullMapper().toJson(selector.selectedKeys()));
        //使用selector的select方法，查看是否有准备好的对象
        while (selector.select() > 0) {

            for (SelectionKey sk : selector.selectedKeys()) {
                //从selector上的已选择key集中删除正在处理的SelectionKey
                selector.selectedKeys().remove(sk);
                //如果是连接请求
                if (sk.isAcceptable()) {
                    SocketChannel sc = server.accept();
                    //得到的加入连接
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    //将sk对应的Channel设置成准备接收其他请求
                    sk.interestOps(SelectionKey.OP_ACCEPT);
                    logger.info("all keys number is" + selector.keys().size() + " two");
                    logger.info("selected keys number is" + selector.selectedKeys().size() + " two");
                }
                //如果是到达请求(注意ServerSocketChannel只有accept和read两种模式)
                if (sk.isReadable()) {
                    SocketChannel sc = (SocketChannel) sk.channel();
                    //定义准备执行读取数据的缓冲对象
                    ByteBuffer buff = ByteBuffer.allocate(1024);
                    String content = "";
                    logger.info("all keys number is" + selector.keys().size() + " three");
                    logger.info("selected keys number is" + selector.selectedKeys().size() + " three");
                    //开始读取数据
                    try {
                        while (sc.read(buff) > 0) {
                            buff.flip();
                            content += charset.decode(buff);
                        }
                        System.out.println("读取到的数据：" + content);
                        //将sk对应的Channel设置成准备下一次读取
                        sk.interestOps(SelectionKey.OP_READ);
                    }
                    //如果捕获到该sk对应的Channel出现了异常，即表明该Channel对应的Client出现了问题，所以从Selector中取消sk的注册
                    catch (IOException e) {
                        sk.cancel();
                        if (sk.channel() != null) {
                            sk.channel().close();
                        }
                    }
                    //此处如果读到的信息不是空的，就将这个信息发送给所有注册进Selector的客户端中(代码上表现为，写入SocketChannel中)
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
        logger.info("all keys number is" + selector.keys().size() + " four");
        logger.info("selected keys number is" + selector.selectedKeys().size() + " four");
    }

    public static void main(String[] args) throws IOException {
        new NioServer().init();
    }
}
