package com.webrelativeonedemo.biosocketdemo.singlesocketserverandclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private final static Logger logger = LoggerFactory.getLogger(SocketServer.class);

    public void socketServerStart(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        Socket socket = serverSocket.accept();

        //然后向客户端返回信息
        OutputStream out = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.print("服务器端返回的信息~~");
        printWriter.flush();

        //得到输入流并打印客户端输入的内容
        InputStream in = socket.getInputStream();
        int len;
        byte[] bytes = new byte[64];
        while ((len = in.read(bytes)) != -1) {
            logger.info(new String(bytes, 0, len));
        }

        //关闭流（高级流关闭的时候会自动关闭低级流）
        in.close();
        printWriter.close();
    }

    public static void main(String[] args) throws IOException {
        SocketServer socketServer = new SocketServer();
        socketServer.socketServerStart(8080);
    }
}
