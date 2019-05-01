package com.tabasoft.jsonrpc.core.model;

import lombok.Data;

@Data
public class RpcRequestEntity {
    private String jsonrpc;
    private String method;
    private String params;
    private String id;
}
