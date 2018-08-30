package com.nettyonedemo.nettyrpcexprient.client;

/**
 * 客户端响应对象，将相关的响应类型封装成了对象
 */
public class RPCResponse {

    /**
     * 请求Id，可用于请求响应的校验;
     **/
    private String requestId;

    /**
     * 请求类型
     **/
    private String type;

    /**
     * 请求的内容
     **/
    private Object payload;

    public RPCResponse(String requestId, String type, Object payload) {
        this.requestId = requestId;
        this.type = type;
        this.payload = payload;
    }

    public String getRequestId() {
        return requestId;
    }

    public RPCResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getType() {
        return type;
    }

    public RPCResponse setType(String type) {
        this.type = type;
        return this;
    }

    public Object getPayload() {
        return payload;
    }

    public RPCResponse setPayload(Object payload) {
        this.payload = payload;
        return this;
    }
}
