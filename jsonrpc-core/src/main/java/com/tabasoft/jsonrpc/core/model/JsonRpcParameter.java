package com.tabasoft.jsonrpc.core.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JsonRpcParameter {
    private String type;
    private String name;
    private String description;
    private String schema;
}
