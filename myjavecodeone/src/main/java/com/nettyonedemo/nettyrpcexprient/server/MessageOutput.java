package com.nettyonedemo.nettyrpcexprient.server;

public class MessageOutput {


    private String requestId;

    private String type;

    private Object payload;

    public MessageOutput(String requestId, String type, Object payload) {
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

    public Object getPayload() {
        return payload;
    }
}
