package com.nettyonedemo.nettyrpcexprient.client;

/**
 * 客户端异常类
 */
public class RPCException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RPCException() {
    }

    public RPCException(String message) {
        super(message);
    }

    public RPCException(String message, Throwable cause) {
        super(message, cause);
    }

    public RPCException(Throwable cause) {
        super(cause);
    }
}
