package com.nettyonedemo.nettyrpcexprient.client;


import com.cpsdb.base.mapper.JsonMapper;
import com.google.common.base.Charsets;
import com.nettyonedemo.nettyrpcexprient.util.RequestId;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 类似客户端的管理类，用于组装各个部件,同时提供一些api，包括启动客户端;
 */
public class RPCClient {

    private String ip;
    private int port;
    private Socket socket;
    private DataInputStream input;
    private OutputStream output;

    public RPCClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private void connect() throws IOException {
        SocketAddress addr = new InetSocketAddress(ip, port);
        socket = new Socket();
        socket.connect(addr, 5000);
        //二进制流
        input = new DataInputStream(socket.getInputStream());
        output = socket.getOutputStream();
    }

    public void close() {
        try {
            socket.close();
            socket = null;
            input = null;
            output = null;
        } catch (IOException e) {
        }
    }

    private void writeStr(DataOutputStream output, String s) throws IOException {
        //先写记录的长度，然后写入数据;这样在服务器端取出数据的时候，也是先读出数据长度，然后根据长度定义字节数组，再把具体的数据读入该字节数组中;
        output.writeInt(s.length());
        output.write(s.getBytes(Charsets.UTF_8));
    }

    private String readStr() throws IOException {
        int len = input.readInt();
        byte[] bytes = new byte[len];
        input.readFully(bytes);
        return new String(bytes, Charsets.UTF_8);
    }

    public RPCClient rpc(String type, Class<?> clazz) {
        //rpc响应类型注册快速入口
        ResponseRegistry.register(type, clazz);
        return this;
    }

    public void cast(String type, Object payload) {
        //单向消息，服务器不返回结果
        try {
            this.sendInternal(type, payload, true);
        } catch (IOException e) {
            throw new RPCException(e);
        }
    }

    public Object send(String type, Object payload) {
        //普通rpc请求，正常获取响应
        try {
            return this.sendInternal(type, payload, false);
        } catch (IOException e) {
            throw new RPCException(e);
        }
    }


    /**
     * 用于发送之前在程序内部组装数据
     *
     * @param type    发送的类型，自定义，需要和服务器端注册了相应处理器和类类型的type名一致;
     * @param payload 发送内容
     * @param cast    用于设置是否立即获取请求服务器端的响应;false表示立即获得并读取响应，true表示不立即获取响应——感觉类似UDP与TCP的差别
     * @return 服务端相应内容;
     * @throws IOException
     */
    private Object sendInternal(String type, Object payload, boolean cast) throws IOException {

        if (output == null) {
            connect();
        }
        //发送之前随机获得请求id;
        String requestId = RequestId.nextId();
        //内存流，用于暂时保存数据;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //主要用于一段一段的放数据,其次就是二进制流有toByteArray()方法，可以将数据转换为字节数组。
        //如果是其他输出流想得到字节数组，则只能通过输出流的write读到字节数组中。
        DataOutputStream buf = new DataOutputStream(bytes);

        //一段一段的将数据传入并刷新。同时因为bytes已经被包含在了DataOutputStream中，所以此时bytes中也有数据;
        writeStr(buf, requestId);
        writeStr(buf, type);
        writeStr(buf, JsonMapper.buildNonNullMapper().toJson(payload));
        buf.flush();

        //从内存缓冲流中取出数据，得到总的流大小对应的字节数组，用于发送;
        byte[] fullLoad = bytes.toByteArray();

        try {
            //发送请求
            output.write(fullLoad);
        } catch (IOException e) {
            //网络异常则重连
            close();
            connect();
            output.write(fullLoad);
        }

        if (!cast) {
            //注意读取的时候需要按照输入的顺序来读取，即先requestId,然后是type，最后是payload;
            //rpc普通请求，要立即获取响应;
            String reqId = readStr();
            //校验请求ID是否匹配(即请求服务器端和响应时使用requestId来判断是否一样);
            //不一样则先关闭然后直接抛错
            if (!requestId.equals(reqId)) {
                close();
                throw new RPCException("请求ID不匹配");
            }
            String typ = readStr();
            Class<?> clazz = ResponseRegistry.get(typ);

            //查看响应类型是否已经提前注册，否则报错;
            //可知不管是服务器端还是客户端，只要是接收方，都需要通过封装的接收对象中的type类型来判断，该类型是否已经被注册（因为涉及到反序列化的问题）;
            //可知rpc中任何发送方都需要先注册类型，这样接收方才能通过该类型进行反序列化;
            if (clazz == null) {
                throw new RPCException("未注册的响应类型 " + typ);
            }
            //最后前面判定都没问题,则从流中得到数据并反序列化，同时返回
            String payId = readStr();
            return JsonMapper.buildNonNullMapper().fromJson(payId, clazz);
        }
        //如果不立即响应则返回null;
        return null;
    }
}
