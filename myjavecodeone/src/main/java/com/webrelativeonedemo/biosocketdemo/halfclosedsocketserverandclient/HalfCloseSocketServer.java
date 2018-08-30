package com.webrelativeonedemo.biosocketdemo.halfclosedsocketserverandclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HalfCloseSocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(30000);

        Socket s = ss.accept();
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        pw.println("来自服务器的第一行数据");
        pw.println("来自服务器的第二行数据");
//        s.shutdownOutput();
        System.out.println(s.isClosed());
        pw.println("来自服务器的第三行数据");
        pw.flush();
        Scanner scanner = new Scanner(s.getInputStream());

        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }

        scanner.close();
        s.close();
        ss.close();
    }
}
