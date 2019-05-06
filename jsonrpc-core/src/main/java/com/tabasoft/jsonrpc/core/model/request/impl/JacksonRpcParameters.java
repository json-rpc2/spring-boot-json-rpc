package com.tabasoft.jsonrpc.core.model.request.impl;

import com.tabasoft.jsonrpc.core.model.request.RpcMethodArguments;
import com.tabasoft.jsonrpc.core.model.request.RpcParameters;
import lombok.Builder;

@Builder
public class JacksonRpcParameters implements RpcParameters {

    private String id;
    private String version;
    private String methodName;
    private RpcMethodArguments methodArguments;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public String getMethodName() {
        return this.methodName;
    }

    @Override
    public RpcMethodArguments getMethodArguments() {
        return this.methodArguments;
    }
}
