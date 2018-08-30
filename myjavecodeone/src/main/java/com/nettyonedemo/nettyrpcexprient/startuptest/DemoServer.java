package com.nettyonedemo.nettyrpcexprient.startuptest;

import com.nettyonedemo.nettyrpcexprient.handle.ExpRequestHandler;
import com.nettyonedemo.nettyrpcexprient.handle.FibRequestHandler;
import com.nettyonedemo.nettyrpcexprient.common.ExpRequest;
import com.nettyonedemo.nettyrpcexprient.server.RPCServer;

public class DemoServer {

    public static void main(String[] args) {
        //得到服务器对象
        RPCServer server = new RPCServer("127.0.0.1", 8888, 2, 16);
        //进行注册
        server.service("fib", Integer.class, new FibRequestHandler())
                .service("exp", ExpRequest.class, new ExpRequestHandler());
        //启动服务器
        server.start();
    }
}
