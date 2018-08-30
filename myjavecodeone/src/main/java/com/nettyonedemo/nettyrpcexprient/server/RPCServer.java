package com.nettyonedemo.nettyrpcexprient.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * 类似服务器端的管理类，用于组装各个部件并启动客户端;
 */
public class RPCServer {

    private String ip;
    private int port;
    /**
     * 用来处理网络流的读写线程数量(Netty中的服务器会有两个Channel——根据Netty的知识，一个Channel会和一个EventLoop以及一个线程唯一绑定，此处的ioThread
     * 就是所谓的父Channel，下一个用于处理子业务的线程就是所谓的子Channel);
     * 此处不管是父Channel还是子Channel都使用了线程池的概念;
     */
    private int ioThread;
    /**
     * 用于业务处理的计算线程数量;
     */
    private int workerThreads;

    /**
     * 为RPC服务器加入参数
     *
     * @param ip            ip地址
     * @param port          端口号
     * @param ioThread      io线程数
     * @param workerThreads 业务计算线程数
     */
    public RPCServer(String ip, int port, int ioThread, int workerThreads) {
        this.ip = ip;
        this.port = port;
        this.ioThread = ioThread;
        this.workerThreads = workerThreads;
    }

    private ServerBootstrap bootstrap;

    private EventLoopGroup group;
    /**
     * 业务处理器;
     */
    private MessageServerChannel collector;
    /**
     * 引导程序后绑定具体的地址端口后返回连接得到的Channel;
     */
    private Channel serverChannel;

    /**
     * 注册服务的快捷方式——具体来说，注册了两个东西，一个是类型的Class对象，用于反序列化时使用;一个是类型的处理器;
     *
     * @param type     类型名字
     * @param reqClass 类型的Class类型
     * @param handler  类型对应的处理器
     * @return 返回自身，用于链式调用;
     */
    public RPCServer service(String type, Class<?> reqClass, IMessageHandler<?> handler) {
        MessageRegistry.register(type, reqClass);
        MessageHandler.register(type, handler);
        return this;
    }

    /**
     * 启动RPC服务器端的方法，也叫引导
     */
    public void start() {

        //构造引导类
        bootstrap = new ServerBootstrap();

        //将具体的线程和EventLoopGroup绑定并加入引导类;
        //底层实际上调用了SingleThreadEventExecutor()的构造器创建相应线程数量的线程池;
        group = new NioEventLoopGroup(ioThread);
        bootstrap.group(group);

        //得到Channel的业务处理类对象
        collector = new MessageServerChannel(workerThreads);

        //得到自定义的编码器和解码器对象（因为有自定义的解码器和编码器，所以才能在ChannelHandler的实现类方法中直接将接收到的Object msg强转为本地的类型;）
        MessageEncoder encoder = new MessageEncoder();
        MessageDecoder decoder = new MessageDecoder();

        //进一步配置网络连接方面的参数
        //此处的childChannel中实际上是用于处理逻辑的Channel，所以是ServerSocketChannel（连接后生成Socket）——父Channel是用于连接的;
        bootstrap.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //往ChannelPipeline中加东西，注意是最后加ChannelHandler
                ChannelPipeline pipeline = ch.pipeline();
                //如果客户端60s没有任何请求，就关闭客户端链接;
                pipeline.addLast(new ReadTimeoutHandler(60));
                //加上编、解码器，便于之后的强转;
                pipeline.addLast(decoder).addLast(encoder);
                //最后加入业务逻辑
                pipeline.addLast(collector);
            }
        });

        //设置模板属性
        //客户端套接字接受的队列大小
        bootstrap.option(ChannelOption.SO_BACKLOG, 100)
                //reuse addr 避免端口冲突
                .option(ChannelOption.SO_REUSEADDR, true)
                //关闭小流合并，保证消息及时性
                .option(ChannelOption.TCP_NODELAY, true)
                //长时间没动静的链接自动关闭
                .option(ChannelOption.SO_KEEPALIVE, true);

        //之后绑定具体的地址端口，并得到具体的channel
        //此处是直接使用的channel方法得到完成以上配置的channel对象。如果最后不是使用的channel()方法，而是使用的addListener()、await()、sync()等
        //异步的方法，则返回的是一个封装了回调方法的对象——ChannelFuture，之后可以根据这个对象的方法得到异步操作的结果;
        serverChannel = bootstrap.bind(this.ip, this.port).channel();
    }

    public void stop() {
        //先关闭服务器端用于连接的套接字，即上面说服务器端两个Channel中的父Channel;
        serverChannel.close();
        //停止线程（netty中一个EventLoop就和一个线程绑定）
        group.shutdownGracefully();
        //最后关闭业务线程，即上面说的服务器端两个Channel中的子Channel
        collector.closeGracefully();
    }
}
