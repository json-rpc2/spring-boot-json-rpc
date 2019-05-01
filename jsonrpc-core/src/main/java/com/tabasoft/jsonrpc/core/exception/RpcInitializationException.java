package com.tabasoft.jsonrpc.core.exception;

public class RpcInitializationException extends RuntimeException {
    public RpcInitializationException(String message) {
        super(message);
    }

    public RpcInitializationException(String message, Throwable e) {
        super(message, e);
    }
}
