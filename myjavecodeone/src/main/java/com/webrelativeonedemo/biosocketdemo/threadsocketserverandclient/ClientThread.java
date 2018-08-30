package com.webrelativeonedemo.biosocketdemo.threadsocketserverandclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Socket s;
    private BufferedReader br = null;

    ClientThread(Socket s) throws IOException {
        this.s = s;
        this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {

        String content = null;
        //不断的读取Socket输入流中的内容并打印出来
        try {
            while ((content = br.readLine()) != null) {
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
