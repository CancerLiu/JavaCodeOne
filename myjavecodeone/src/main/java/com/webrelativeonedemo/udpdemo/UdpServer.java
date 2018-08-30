package com.webrelativeonedemo.udpdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer {
    public static final int DEST_PORT = 30000;

    private static final int DATA_LEN = 4096;

    byte[] inBuff = new byte[DATA_LEN];

    private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);

    private DatagramPacket outPacket = null;

    String[] books = new String[]{
            "不是爱风尘",
            "似被前缘误",
            "花落花开自有时",
            "总赖东君主"
    };

    public void init() throws IOException {
        DatagramSocket socket = new DatagramSocket(DEST_PORT);
        for (int i = 0; i < 1000; i++) {
            socket.receive(inPacket);
            System.out.println(inBuff == inPacket.getData());
            System.out.println(new String(inBuff, 0, inPacket.getLength()));
            byte[] sendData = books[i % 4].getBytes();
            outPacket = new DatagramPacket(sendData, sendData.length, inPacket.getSocketAddress());
            socket.send(outPacket);
        }
    }

    public static void main(String[] args) throws IOException {
        new UdpServer().init();
    }
}
