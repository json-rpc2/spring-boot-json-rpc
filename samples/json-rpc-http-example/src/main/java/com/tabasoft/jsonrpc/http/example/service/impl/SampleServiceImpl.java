package com.tabasoft.jsonrpc.http.example.service.impl;

import com.tabasoft.jsonrpc.schema.annotation.JsonRpcMethod;
import com.tabasoft.jsonrpc.schema.annotation.JsonRpcService;

@JsonRpcService
public class SampleServiceImpl {
    @JsonRpcMethod(name = "sample.getNames", description = "method for do something")
    public String getNames(String name) {
        return name + "s";
    }

    @JsonRpcMethod(name = "sample.addName", description = "method for do something")
    public String addName(String name) {
        return name + "sadf";
    }
}
