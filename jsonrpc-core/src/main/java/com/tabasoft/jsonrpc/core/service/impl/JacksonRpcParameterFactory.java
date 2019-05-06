package com.tabasoft.jsonrpc.core.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tabasoft.jsonrpc.core.model.request.RpcMethodArguments;
import com.tabasoft.jsonrpc.core.model.request.RpcParameters;
import com.tabasoft.jsonrpc.core.model.request.impl.JacksonRpcArrayMethodArguments;
import com.tabasoft.jsonrpc.core.model.request.impl.JacksonRpcObjectMethodArguments;
import com.tabasoft.jsonrpc.core.model.request.impl.JacksonRpcParameters;
import com.tabasoft.jsonrpc.core.service.RpcParameterFactory;

public class JacksonRpcParameterFactory implements RpcParameterFactory<JsonNode> {

    public static final String VERSION_FIELD = "jsonrpc";
    public static final String PARAMS_FIELD = "params";
    public static final String ID_FIELD = "id";
    public static final String METHOD_FIELD = "method";

    @Override
    public RpcParameters makeParameters(JsonNode source) {
        return JacksonRpcParameters.builder()
                .id(source.get(ID_FIELD).asText())
                .version(source.get(VERSION_FIELD).asText())
                .methodName(source.get(METHOD_FIELD).asText())
                .methodArguments(createParameters(source.get(PARAMS_FIELD)))
                .build();
    }

    private RpcMethodArguments createParameters(JsonNode paramsNode) {
        if (paramsNode.isArray()) {
            return new JacksonRpcArrayMethodArguments((ArrayNode) paramsNode);
        } else {
            return new JacksonRpcObjectMethodArguments((ObjectNode) paramsNode);
        }
    }
}
