package com.webrelativeonedemo.urldemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class URLConnectDemo {

    /**
     * 向指定URL发送GET方式的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，格式须满足name1=value&name2=value2的形式
     * @return 具体的响应
     * @throws IOException
     */
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

    public static String sendGet(String url, String param) throws IOException {
        String result = "";
        String urlName = url + "?" + param;
        //打开和URL之间的连接
        URL realUrl = new URL(urlName);
        URLConnection conn = realUrl.openConnection();

        //设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");

        //建立实际的连接
        conn.connect();
        //获取所有的响应头
        Map<String, List<String>> map = conn.getHeaderFields();
        //遍历所有的响应头字段
        for (String key : map.keySet()) {
            System.out.println(key + "------>" + map.get(key));
        }


        //定义BufferedReader输入流来读取URL的响应(这里使用到了转换流)
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        while ((line = in.readLine()) != null) {
            result += "\n" + line;
        }
        return result;
    }
}
