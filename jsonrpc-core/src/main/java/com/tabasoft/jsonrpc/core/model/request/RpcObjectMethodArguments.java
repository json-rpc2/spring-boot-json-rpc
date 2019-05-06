package com.tabasoft.jsonrpc.core.model.request;

import com.tabasoft.jsonrpc.core.model.configuration.JsonRpcMethodDefinition;

import java.util.Map;
import java.util.stream.Stream;

public abstract class RpcObjectMethodArguments implements RpcMethodArguments {

    protected abstract Map<String, Object> getMappedArguments(JsonRpcMethodDefinition methodDefinition);

    @Override
    public Object[] getPositionalMethodArguments(JsonRpcMethodDefinition methodDefinition) {
        var mappedArguments = getMappedArguments(methodDefinition);
        return Stream.of(methodDefinition.getParameterNames()).map(mappedArguments::get).toArray();
    }

    @Override
    public Map<String, Object> getMappedMethodArguments(JsonRpcMethodDefinition methodDefinition) {
        return getMappedArguments(methodDefinition);
    }
}
