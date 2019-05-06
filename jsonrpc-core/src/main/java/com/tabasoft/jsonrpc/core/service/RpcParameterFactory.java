package com.tabasoft.jsonrpc.core.service;

import com.tabasoft.jsonrpc.core.model.request.RpcParameters;

public interface RpcParameterFactory<T> {
    RpcParameters makeParameters(T source);
}
