package com.webrelativeonedemo.biosocketdemo.singlesocketserverandclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {

    private final static Logger logger = LoggerFactory.getLogger(SocketClient.class);

    public void socketClientStartOne(int port) throws IOException {
        Socket socket = new Socket("127.0.0.1", port);
        OutputStream outputStream = socket.getOutputStream();

        //向服务器发送信息
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.print("客户端1请求的信息~~");
        printWriter.flush();

        //接收服务器返回的信息
        InputStream inputStream = socket.getInputStream();
        int len;
        byte[] bytes = new byte[64];
        while ((len = inputStream.read(bytes)) != -1) {
            logger.info("来自服务器的信息" + new String(bytes, 0, len));
        }

        //关闭流
        outputStream.close();
        printWriter.close();
    }

    public void socketClientStartTwo(int port) throws IOException {
        Socket socket = new Socket("127.0.0.1", port);
        OutputStream outputStream = socket.getOutputStream();

        //向服务器发送信息
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.print("客户端2请求的信息~~");
        printWriter.flush();

        //接收服务器返回的信息
        InputStream inputStream = socket.getInputStream();
        int len;
        byte[] bytes = new byte[64];
        while ((len = inputStream.read(bytes)) != -1) {
            logger.info("来自服务器的信息" + new String(bytes, 0, len));
        }

        //关闭流
        outputStream.close();
        printWriter.close();
    }

    public static void main(String[] args) throws IOException {
        SocketClient socketClient = new SocketClient();
        socketClient.socketClientStartOne(8080);
        socketClient.socketClientStartTwo(8080);
    }
}
