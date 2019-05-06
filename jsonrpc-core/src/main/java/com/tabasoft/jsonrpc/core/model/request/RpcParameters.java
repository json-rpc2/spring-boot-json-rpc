package com.tabasoft.jsonrpc.core.model.request;

public interface RpcParameters {
    String getId();

    String getVersion();

    String getMethodName();

    RpcMethodArguments getMethodArguments();
}
