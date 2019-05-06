package com.tabasoft.jsonrpc.core.model.request;

import com.tabasoft.jsonrpc.core.exception.DefaultExceptionCodes;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionMessages;
import com.tabasoft.jsonrpc.core.exception.RpcException;
import com.tabasoft.jsonrpc.core.model.configuration.JsonRpcMethodDefinition;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class RpcArrayMethodArguments implements RpcMethodArguments {
    protected abstract Object[] getArrayArguments(JsonRpcMethodDefinition methodDefinition);

    @Override
    public Object[] getPositionalMethodArguments(JsonRpcMethodDefinition methodDefinition) {
        return getArrayArguments(methodDefinition);
    }

    @Override
    public Map<String, Object> getMappedMethodArguments(JsonRpcMethodDefinition methodDefinition) {
        var arrayArguments = getArrayArguments(methodDefinition);
        var parameterNames = methodDefinition.getParameterNames();
        if (arrayArguments.length != parameterNames.length) {
            throw new RpcException(DefaultExceptionMessages.INVALID_PARAMS, DefaultExceptionCodes.INVALID_PARAMS);
        }

        return IntStream.range(0, arrayArguments.length)
                .boxed()
                .collect(Collectors.toMap(i -> parameterNames[i], i -> arrayArguments[i]));
    }
}
