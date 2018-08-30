package com.webrelativeonedemo.biosocketdemo.halfclosedsocketserverandclient;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HalfCloseSocketClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 30000);

        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.println("来自服务器的第一条信息");
        pw.flush();

        InputStreamReader reader = new InputStreamReader(socket.getInputStream());
        int len;
        char[] chars = new char[64];
        while ((len = reader.read(chars)) != -1) {
            System.out.println(new String(chars, 0, len));
        }

        pw.close();
        reader.close();
    }
}
