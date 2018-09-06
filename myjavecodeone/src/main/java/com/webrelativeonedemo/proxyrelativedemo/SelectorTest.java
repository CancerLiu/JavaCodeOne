package com.webrelativeonedemo.proxyrelativedemo;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class SelectorTest {
    //下面是代理服务器的地址和端口
    //随便一个代理服务器的地址和端口
    final String PROXY_ADDR = "139.82.12.188";
    final int PROXY_PORT = 3124;
    //定义需要访问的网站地址
    String urlStr = "http://baidu.com";

    public void init() throws IOException {
        //注册默认的代理选择器
        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                //本程序总是返回某个固定的代理服务器
                List<Proxy> result = new ArrayList<>();
                result.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_ADDR, PROXY_PORT)));
                return result;
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                System.out.println("无法连接到指定代理服务器！");
            }
        });

        URL url = new URL(urlStr);
        //没有指定代理服务器，直接打开连接
        URLConnection conn = url.openConnection();

    }
}
