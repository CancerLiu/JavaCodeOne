package com.nettyonedemo.nettyrpcexprient.server;

import com.cpsdb.base.mapper.JsonMapper;

public class MessageInput {

    private String requestId;

    private String type;

    private String payload;

    public MessageInput(String requestId, String type, String payload) {
        this.type = type;
        this.requestId = requestId;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public String getRequestId() {
        return requestId;
    }

    public <T> T getPayload(Class<T> clazz) {
        if (payload == null) {
            return null;
        }
        return JsonMapper.buildNonNullMapper().fromJson(payload, clazz);
    }
}
