package com.nettyonedemo.nettyrpcexprient.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务器端消息类型处理器注册。即指定哪种类型消息体使用哪种类型的处理器处理
 */
public class MessageHandler {

    private static Map<String, IMessageHandler<?>> handlers = new HashMap<>();
    //聚合一个默认处理器;
    public static DefaultHandler defaultHandler = new DefaultHandler();

    public static void register(String type, IMessageHandler<?> handler) {
        handlers.put(type, handler);
    }

    public static IMessageHandler<?> get(String type) {
        return handlers.get(type);
    }
}
