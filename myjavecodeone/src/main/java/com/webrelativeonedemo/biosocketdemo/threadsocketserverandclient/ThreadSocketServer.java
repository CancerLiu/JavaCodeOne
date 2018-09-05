package com.webrelativeonedemo.biosocketdemo.threadsocketserverandclient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * 任何被传入进来的客户端消息都会通过线程类被广播到其他所有的客户端中
 */
public class ThreadSocketServer {
    //此处使用synchronizedList(List<T> list)方法来获取一个线程安全的类。
    public static List<Socket> socketList = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(30000);

        while (true) {
            Socket socket = ss.accept();

            socketList.add(socket);

            //（1）普通开启线程的方法
//            new Thread(new ServerThread(socket)).start();
            //（2）通过线程池工厂类来开启线程的方法————此处将创建线程并管理的功能单独让一个线程类负责
            Executors.newCachedThreadPool().submit(new ServerThread(socket));
            //（3）手动创建线程池并开启的方法
//            int corePoolSize = 5;
//            int maxPoolSize = 10;
//            long keepAliveTime = 5000;
//
//            //创建线程工厂类
//            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("\"Orders-%d\"").build();
//
//            ExecutorService singleThreadPool = new ThreadPoolExecutor(corePoolSize,
//                    maxPoolSize,
//                    keepAliveTime,
//                    TimeUnit.MICROSECONDS,
//                    new LinkedBlockingQueue<Runnable>(), threadFactory);
//
//            singleThreadPool.execute(new ServerThread(socket));
        }
    }
}
