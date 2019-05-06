package com.tabasoft.jsonrpc.core.model.request;

import com.tabasoft.jsonrpc.core.model.configuration.JsonRpcMethodDefinition;

import java.util.Map;

public interface RpcMethodArguments {
    Object[] getPositionalMethodArguments(JsonRpcMethodDefinition methodDefinition);

    Map<String, Object> getMappedMethodArguments(JsonRpcMethodDefinition methodDefinition);
}
