package com.nettyonedemo.nettyrpcexprient.startuptest;

import com.nettyonedemo.nettyrpcexprient.client.RPCClient;
import com.nettyonedemo.nettyrpcexprient.common.ExpRequest;
import com.nettyonedemo.nettyrpcexprient.common.ExpResponse;

public class DemoClient {

    private RPCClient client;

    public DemoClient(RPCClient client) {
        this.client = client;
        //注册服务返回类型
        this.client.rpc("fib_res", Long.class).rpc("exp_res", ExpResponse.class);
    }

    public long fib(int n) {
        return (Long) client.send("fib", n);
    }

    public ExpResponse exp(int base, int exp) {
        return (ExpResponse) client.send("exp", new ExpRequest(base, exp));
    }

    public static void main(String[] args) {
        RPCClient client = new RPCClient("127.0.0.1", 8888);
        DemoClient demo = new DemoClient(client);
        for (int i = 0; i < 20; i++) {
            System.out.printf("fib(%d)=%d\n", i, demo.fib(i));
        }
        for (int i = 0; i < 20; i++) {
            ExpResponse res = demo.exp(2, i);
            System.out.printf("exp2(%d) = %d cost=%dns\n", i, res.getValue(), res.getCostInNanos());
        }
    }

}
