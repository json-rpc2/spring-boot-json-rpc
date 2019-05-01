package com.tabasoft.jsonrpc.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RpcResponseEntity {
    private RpcErrorEntity error;
    private Object result;
    private String jsonrpc;
    private String id;
}
