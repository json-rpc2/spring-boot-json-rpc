package com.tabasoft.jsonrpc.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RpcErrorEntity {
    private String message;
    private int code;
}
