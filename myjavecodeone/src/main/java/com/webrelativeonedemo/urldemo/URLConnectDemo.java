package com.webrelativeonedemo.urldemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectDemo {

    public static String sendPost(String url, String param) throws IOException {
        String result = "";

        URL realUrl = new URL(url);

        URLConnection conn = realUrl.openConnection();
        //设置通用请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSTE 6.0; Windows NT 5.1; SV1)");

        //发送POST请求必须设置,依次代表使用POST请求的时候，可以提交值，可以获得返回值
        conn.setDoOutput(true);
        conn.setDoInput(true);

        //转成打印流往外输出
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(param);
        out.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += "\n" + line;
        }
        return result;
    }
}
