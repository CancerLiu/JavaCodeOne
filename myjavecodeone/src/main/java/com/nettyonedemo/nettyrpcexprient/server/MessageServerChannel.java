package com.nettyonedemo.nettyrpcexprient.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ChannelHandler的业务逻辑处理器类，因为有此类的存在，Netty才能做到将网络连接等配置与业务逻辑分离
 */
@ChannelHandler.Sharable
public class MessageServerChannel extends ChannelInboundHandlerAdapter {

    private ThreadPoolExecutor executor;

    /**
     * 在构造器中根据输入的线程号构造业务线程池
     *
     * @param workThread 最大线程数;
     */
    public MessageServerChannel(int workThread) {
        //业务队列最大1000，避免堆积。Queue是一个先进先出队列;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1000);

        //为业务线程命名同时构造线程工厂
        ThreadFactory factory = new ThreadFactory() {
            //相比于Integer，AtomicInteger是线程安全的，并且提供了相应的方法，来线程安全的获得和操作其值;
            AtomicInteger seq = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("rpc" + seq.getAndIncrement());
                return t;
            }
        };
        //时间超过30s线程自己销毁;
        this.executor = new ThreadPoolExecutor(1, workThread, 30, TimeUnit.SECONDS, queue, factory, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void closeGracefully() {
        // 优雅一点关闭，先通知，再等待，最后强制关闭
        this.executor.shutdown();
        try {
            this.executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        this.executor.shutdownNow();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //客户端发来一个连接
        System.out.println("当前服务器端已连接一个客户端");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("当前服务器端已失去一个客户端连接");
    }

    /**
     * 读取的时候，触发，然后调用我们自己的核心业务方法;
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //因为有解码器，所以此处可以直接强转为MessageInput
        if (msg instanceof MessageInput) {
            System.out.println("读取一条客户端信息");
            //线程池执行的结果就是把读到的数据输入处理器类进行处理，然后将结果封装成对象加入ChannelHandlerContext中，等待引导类引导;
            this.executor.execute(() -> {
                this.handleMessage(ctx, (MessageInput) msg);
            });
        }
    }

    /**
     * 实际核心业务的处理类;
     *
     * @param ctx
     * @param input 具体信息
     */
    private void handleMessage(ChannelHandlerContext ctx, MessageInput input) {
        //得到消息类型，序列化时使用
        Class<?> clazz = MessageRegistry.get(input.getType());

        if (clazz == null) {
            //没注册的消息使用默认的处理器处理并中止方法;
            MessageHandler.defaultHandler.handle(ctx, input.getRequestId(), input);
            return;
        }

        //通过消息真实Class类型得到具体的传输对象;
        Object obj = input.getPayload(clazz);

        //得到实际的处理器并调用处理器的方法进行处理
        @SuppressWarnings("unchecked")
        IMessageHandler<Object> handler = (IMessageHandler<Object>) MessageHandler.get(input.getType());
        if (handler != null) {
            //这里的关键在于，在实现类的handler方法中，最后使用了ctx.writeAndFlush()方法（并且其中将结果封装在了ExpResponse对象中）将消息刷入ChannelPipeline中;
            handler.handle(ctx, input.getRequestId(), obj);
        } else {
            //否则使用默认处理器进行处理
            MessageHandler.defaultHandler.handle(ctx, input.getRequestId(), input);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务端连接客户端出错");
        cause.printStackTrace();
        //关闭当前Channel（或者说客户端）。客户端会有重连的机制;
        ctx.close();
    }
}
