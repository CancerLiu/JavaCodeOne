package com.webrelativeonedemo.biosocketdemo.threadsocketserverandclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.Executors;

public class ThreadSocketClient {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 30000);


//        //手动创建线程池（先创建线程工厂类）
//        int corePoolSize = 5;
//        int maxPoolSize = 10;
//        long keepAliveTime = 5000;
//        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("\"Orders-%d\"").build();
//
//        ExecutorService singleThreadPool = new ThreadPoolExecutor(corePoolSize,
//                maxPoolSize,
//                keepAliveTime,
//                TimeUnit.MICROSECONDS,
//                new LinkedBlockingQueue<Runnable>(), threadFactory);
//
//        singleThreadPool.submit(new ClientThread(s));

        Executors.newCachedThreadPool().submit(new ClientThread(s));

        //同时主线程负责获取用户输入并将信息传给服务器端
        PrintStream ps = new PrintStream(s.getOutputStream());
        String line = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while ((line = br.readLine()) != null) {
            ps.println(line);
        }


    }
}
