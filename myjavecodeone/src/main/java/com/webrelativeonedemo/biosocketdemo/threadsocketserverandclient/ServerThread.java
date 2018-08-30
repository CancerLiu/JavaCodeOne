package com.webrelativeonedemo.biosocketdemo.threadsocketserverandclient;

import java.io.*;
import java.net.Socket;

/**
 * 负责每个线程通信的线程类
 */
public class ServerThread implements Runnable {

    Socket s = null;

    BufferedReader br = null;

    ServerThread(Socket s) throws IOException {
        this.s = s;
        this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        String content = null;

        while ((content = readFromClient()) != null) {

            for (Socket socket : ThreadSocketServer.socketList) {
                try {
                    PrintStream ps = new PrintStream(socket.getOutputStream());
                    ps.println(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private String readFromClient() {
        try {
            System.out.println("读到~");
            return br.readLine();
            //捕获到异常，说明该Socket对应的客户端已经关闭
        } catch (IOException e) {
            //从列表中删除该socket，这样该已经关闭的socket就不会被广播到消息了
            ThreadSocketServer.socketList.remove(s);
        }
        return null;
    }
}
