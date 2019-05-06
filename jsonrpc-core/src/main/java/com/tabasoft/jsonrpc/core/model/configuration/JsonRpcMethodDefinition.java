package com.tabasoft.jsonrpc.core.model.configuration;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
@Builder
public class JsonRpcMethodDefinition {
    private String name;
    private List<JsonRpcParameter> parameters;
    private Object service;
    private Method javaMethod;
    private String[] parameterNames;
    private String description;
}
