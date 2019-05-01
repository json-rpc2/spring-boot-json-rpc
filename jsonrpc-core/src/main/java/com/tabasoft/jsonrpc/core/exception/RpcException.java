package com.tabasoft.jsonrpc.core.exception;

import lombok.Getter;

@Getter
public class RpcException extends RuntimeException {
    private final int code;

    public RpcException(String message, int code) {
        super(message);
        this.code = code;
    }

    public RpcException(String message, int code, Throwable e) {
        super(message, e);
        this.code = code;
    }
}
