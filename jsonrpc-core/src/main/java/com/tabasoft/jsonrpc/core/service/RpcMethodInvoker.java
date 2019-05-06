package com.tabasoft.jsonrpc.core.service;

import com.tabasoft.jsonrpc.core.model.configuration.JsonRpcMethodDefinition;
import com.tabasoft.jsonrpc.core.model.request.RpcParameters;
import com.tabasoft.jsonrpc.core.model.response.RpcResponseEntity;

public interface RpcMethodInvoker {
    RpcResponseEntity invokeMethod(JsonRpcMethodDefinition method, RpcParameters parameters);
}
