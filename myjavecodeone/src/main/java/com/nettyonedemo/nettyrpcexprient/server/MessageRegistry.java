package com.nettyonedemo.nettyrpcexprient.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务器端消息类型注册
 * 发送或者接收的时候需要注册，这样才能用于反序列化
 */
public class MessageRegistry {

    private static Map<String, Class<?>> clazzes = new HashMap<>();

    public static void register(String type, Class<?> clazz) {
        clazzes.put(type, clazz);
    }

    public static Class<?> get(String type) {
        return clazzes.get(type);
    }
}
