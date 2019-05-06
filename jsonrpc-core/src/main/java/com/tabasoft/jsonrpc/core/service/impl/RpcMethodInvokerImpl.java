package com.tabasoft.jsonrpc.core.service.impl;

import com.tabasoft.jsonrpc.core.exception.DefaultExceptionCodes;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionMessages;
import com.tabasoft.jsonrpc.core.exception.RpcException;
import com.tabasoft.jsonrpc.core.model.configuration.JsonRpcMethodDefinition;
import com.tabasoft.jsonrpc.core.model.request.RpcParameters;
import com.tabasoft.jsonrpc.core.model.response.RpcErrorEntity;
import com.tabasoft.jsonrpc.core.model.response.RpcResponseEntity;
import com.tabasoft.jsonrpc.core.service.RpcMethodInvoker;

public class RpcMethodInvokerImpl implements RpcMethodInvoker {
    @Override
    public RpcResponseEntity invokeMethod(JsonRpcMethodDefinition method, RpcParameters parameters) {
        if (method == null) {
            throw new RpcException(DefaultExceptionMessages.METHOD_NOT_FOUND, DefaultExceptionCodes.METHOD_NOT_FOUND);
        }
        var parameterValues = parameters.getMethodArguments().getPositionalMethodArguments(method);
        try {
            var result = method.getJavaMethod().invoke(method.getService(), parameterValues);
            return RpcResponseEntity.builder()
                    .id(parameters.getId())
                    .result(result)
                    .jsonrpc(parameters.getVersion())
                    .build();

        } catch (Exception e) {
            return RpcResponseEntity.builder()
                    .id(parameters.getId())
                    .error(getErrorEntity(e)).build();
        }
    }

    private RpcErrorEntity getErrorEntity(Throwable e) {
        return RpcErrorEntity.builder()
                .code(DefaultExceptionCodes.INTERNAL_ERROR)
                .message(e.getMessage())
                .build();
    }
}
