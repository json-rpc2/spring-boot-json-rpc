package com.tabasoft.jsonrpc.core.service;

import com.tabasoft.jsonrpc.core.model.JsonRpcMethodDefinition;

public interface JsonRpcContext {
    void registerService(Object bean);

    JsonRpcMethodDefinition getMethodByName(String name);
}
