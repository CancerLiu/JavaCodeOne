package com.nettyonedemo.nettyrpcexprient.client;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端响应类型注册类，需要先注册类型，序列化和反序列化的时候会用到;
 */
public class ResponseRegistry {

    public static Map<String, Class<?>> claszzes = new HashMap<>();

    public static void register(String type, Class<?> clazz) {
        claszzes.put(type, clazz);
    }

    public static Class<?> get(String type) {
        return claszzes.get(type);
    }
}
