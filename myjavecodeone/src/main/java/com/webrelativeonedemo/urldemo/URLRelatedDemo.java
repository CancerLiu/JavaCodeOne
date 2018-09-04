package com.webrelativeonedemo.urldemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class URLRelatedDemo {

    private final static Logger logger = LoggerFactory.getLogger(URLRelatedDemo.class);

    /**
     * IP相关类的DEMO
     *
     * @throws IOException
     */
    private void inetAddressTest() throws IOException {
        //InetAddress还有两个子类，Inet4Address和Inet6Address

        String netIp = "www.baidu.com";

        InetAddress inetLocal = InetAddress.getLocalHost();
        InetAddress inetIp = InetAddress.getByName(netIp);
        //此处ip需要从int转来，而不能从String转来;
        byte[] bytes = new byte[]{127, 0, 0, 1};

        logger.info("获取的主机相关信息:" + inetLocal.getHostName() + "       " + Arrays.toString(inetLocal.getAddress()) + "      " + inetLocal.getCanonicalHostName());
        logger.info("获取的IP相关信息:" + inetIp.getHostName() + "     " + inetIp.getCanonicalHostName() + "       " + inetIp.getHostAddress() + "     " + Arrays.toString(inetIp.getAddress()));
        //isReachable是不可控的，可能会被防火墙阻挡，所以返回false也是可能的;
        logger.info("inetLocal:" + "       " + inetLocal.isReachable(2000) + "       " + "inetIp:" + "       " + inetIp.isReachable(5000));
        InetAddress inetAddress = InetAddress.getByAddress(bytes);
        logger.info(inetAddress.getHostName());
    }

    private void decodeAndEncodeDemo() throws UnsupportedEncodingException {
        // 每个中文字符占两个字节，每个字节可以转换成两个十六进制的数字（翻译成十进制对比）
        // 两位16进制数从00到FF,表示0-255的10进制数,也就是8 位(1个字节)的数值形式。
        String keyWord = URLEncoder.encode("百度搜索baidusousuo", "GBK");
        logger.info(keyWord);

        String urlWord = URLDecoder.decode("%B0%D9%B6%C8%CB%D1%CB%F7baidusousuo", "GBK");
        logger.info(urlWord);
    }

    private void URLDemo() throws IOException {
        URL url = new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528954143389&di=4997575a0cd54314d4eb4479b2a0793e&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F96dda144ad345982b391b10900f431adcbef8415.jpg");
        logger.info("hostName:" + url.getHost() + "     path:" + url.getPath() + "      protocol:" + url.getProtocol() + "      port:" + url.getPort() + "      file:" + url.getFile()+"    Query:"+url.getQuery());
        InputStream in = url.openStream();
        FileOutputStream out = new FileOutputStream(new File("H:/MyCode/JavaCodeDocument/Picture/firstPicture.jpg"));

        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = in.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        in.close();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        URLRelatedDemo urlRelatedDemo = new URLRelatedDemo();
//        urlRelatedDemo.inetAddressTest();
//        urlRelatedDemo.decodeAndEncodeDemo();
        urlRelatedDemo.URLDemo();
    }
}
