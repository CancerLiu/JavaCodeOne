package com.webrelativeonedemo.proxyrelativedemo;

import java.io.IOException;
import java.net.*;
import java.util.Properties;
import java.util.Scanner;

public class DefaultProxySelectorTest {
    //定义需要访问的网站地址
    static String urlStr = "http://www.baidu.com";

    public static void main(String[] args) throws URISyntaxException, IOException {
        //获取系统的默认属性
        Properties props = System.getProperties();
        /*下面依次设置代理服务器的属性*/

        //通过系统属性设置HTTP访问所用的代理服务器的主机地址、端口
        props.setProperty("http.proxyHost", "192.168.10.96");
        props.setProperty("http.proxyPort", "8080");
        //通过系统属性设置HTTP访问无须使用代理服务器的主机
        //可以使用*通配符，多个地址用|分隔
        props.setProperty("http.nonProxyHosts", "localhost|192.168.10.*");
        //通过系统属性设置HTTPS访问所用的代理服务器的主机地址和端口
        props.setProperty("https.proxyHost", "192.168.10.96");
        props.setProperty("https.proxyPort", "443");
        /*DefaultProxySelector不支持https.nonProxyHosts属性。
         * DefaultProxySelector直接按http.nonProxyHosts的设置规则处理*/
        //通过系统属性设置FTP访问无须使用代理服务器的主机
        props.setProperty("ftp.nonProxyHosts", "localhost|192.168.10.*");
        //通过系统属性设置SOCKS代理服务器的注解地址和端口
        props.setProperty("socks.ProxyHost", "192.168.10.96");
        props.setProperty("socks.ProxyPort", "1080");
        //获取系统默认的代理选择器
        ProxySelector selector = ProxySelector.getDefault();
        System.out.println("系统默认的代理选择器:" + selector);
        //根据URI动态决定所使用的代理服务器
        System.out.println("系统为ftp://www.crazyit.org选择的代理服务器为:"
                + selector.select(new URI("ftp://www.crazyit.org")));
        URL url = new URL(urlStr);
        //★直接打开连接，默认的代理选择器会使用http.proxyHost、http.proxyPort系统属性设置的代理服务器
        //如果无法连接代理服务器，则默认的代理选择器会尝试直接连接
        URLConnection conn = url.openConnection();
        //设置超时时长
        conn.setConnectTimeout(30000);
        try (Scanner scan = new Scanner(conn.getInputStream(), "utf-8")) {
            //读取远程主机的内容
            while (scan.hasNextLine()) {
                System.out.println(scan.nextLine());
            }
        }

    }
}
