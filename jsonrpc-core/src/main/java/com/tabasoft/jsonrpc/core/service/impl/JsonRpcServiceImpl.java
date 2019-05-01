package com.tabasoft.jsonrpc.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionCodes;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionMessages;
import com.tabasoft.jsonrpc.core.exception.RpcException;
import com.tabasoft.jsonrpc.core.model.RpcResponseEntity;
import com.tabasoft.jsonrpc.core.service.JsonRpcContext;
import com.tabasoft.jsonrpc.core.service.JsonRpcHandler;
import com.tabasoft.jsonrpc.core.util.RpcParameterUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


@Service
@RequiredArgsConstructor
public class JsonRpcServiceImpl implements JsonRpcHandler {
    private static final Logger logger = LoggerFactory.getLogger(JsonRpcServiceImpl.class);

    private final JsonRpcContext rpcContext;

    @Override
    public String handleRequest(BufferedReader requestReader) {
        var mapper = new ObjectMapper();
        try {
            var node = mapper.readTree(requestReader);
            return handleRequest(node);

        } catch (IOException e) {
            throw new RpcException(DefaultExceptionMessages.PARSER_ERROR, DefaultExceptionCodes.PARSE_ERROR);
        }
    }


    @Override
    public String handleRequest(JsonNode node) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        if (node.isObject()) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(invokeSingleMethod(((ObjectNode) node)));
        } else {
            handleBatchRequest(((ArrayNode) node));
        }
        return null;
    }

    private RpcResponseEntity invokeSingleMethod(ObjectNode node) {
        var version = node.get("jsonrpc").asText();
        var params = node.get("params");
        var id = node.get("id");
        var method = node.get("method").asText();

        var rpcMethod = rpcContext.getMethodByName(method);
        var parameterValues = RpcParameterUtils.getRpcParameters(params, rpcMethod);
        try {
            var result = rpcMethod.getJavaMethod().invoke(rpcMethod.getService(), parameterValues);
            return RpcResponseEntity
                    .builder()
                    .result(result)
                    .jsonrpc(version)
                    .id(id.asText())
                    .build();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RpcException("RPC Method invocation error", DefaultExceptionCodes.INTERNAL_ERROR);
        }
    }

    private void handleBatchRequest(ArrayNode node) {
    }


}
