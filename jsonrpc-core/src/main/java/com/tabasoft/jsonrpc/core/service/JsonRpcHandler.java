package com.tabasoft.jsonrpc.core.service;

import com.tabasoft.jsonrpc.core.model.RpcRequestEntity;
import com.tabasoft.jsonrpc.core.model.RpcResponseEntity;

import java.util.Collection;

public interface JsonRpcHandler {
    RpcResponseEntity handleRequest(RpcRequestEntity request);

    Collection<RpcResponseEntity> handleRequest(Collection<RpcRequestEntity> request);
}
