package com.nettyonedemo.nettyrpcexprient.util;

import java.util.UUID;

public class RequestId {
    public static String nextId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
