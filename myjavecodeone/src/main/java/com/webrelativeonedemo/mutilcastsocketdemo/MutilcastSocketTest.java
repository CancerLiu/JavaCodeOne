package com.webrelativeonedemo.mutilcastsocketdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class MutilcastSocketTest implements Runnable {
    //多点广播IP地址
    private static final String BROADCAST_IP = "230.0.0.1";

    public static final int BROADCAST_PORT = 30000;

    private static final int DATA_LEN = 4096;

    private MulticastSocket socket = null;

    private InetAddress broadcastAddress = null;

    byte[] inBuff = new byte[DATA_LEN];

    private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);

    private DatagramPacket outPacket = null;

    //这个方法相当于主线程，用于从键盘得到的数据，并发送出去(发送到共享IP处)。
    public void init() throws IOException {
        try {
            Scanner scan = new Scanner(System.in);
            //创建MulticastSocket对象同时制定端口号（因为要用于接收数据）
            socket = new MulticastSocket(BROADCAST_PORT);
            broadcastAddress = InetAddress.getByName(BROADCAST_IP);
            //将该socket加入指定的多点广播地址
            socket.joinGroup(broadcastAddress);
            //设置本MulticastSocket发送的数据报会被回送到自身
            socket.setLoopbackMode(false);
            //初始化发送用的DatagramPacket
            outPacket = new DatagramPacket(new byte[0], 0, broadcastAddress, BROADCAST_PORT);
            //启动以本实例的run()方法作为线程执行体的线程
            Executors.newCachedThreadPool().execute(this);

            //键盘输入
            while (scan.hasNextLine()) {
                byte[] bytes = scan.nextLine().getBytes();
                outPacket.setData(bytes);
                socket.send(outPacket);
            }
        } finally {
            socket.close();
        }
    }

    @Override
    public void run() {
        while (true) {
            //该线程用于读取Socket中的数据，放在inPacket中
            try {
                socket.receive(inPacket);
                System.out.println("聊天信息：" + new String(inBuff, 0, inPacket.getLength()));
            } catch (IOException e) {
                e.printStackTrace();
                //如果报错就将该广播类从共享IP处移除。
                try {
                    if (socket != null) {
                        socket.leaveGroup(broadcastAddress);
                        socket.close();
                    }
                    System.exit(1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new MutilcastSocketTest().init();
    }
}
