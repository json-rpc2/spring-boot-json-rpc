package com.tabasoft.jsonrpc.core.model.request.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionCodes;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionMessages;
import com.tabasoft.jsonrpc.core.exception.RpcException;
import com.tabasoft.jsonrpc.core.model.configuration.JsonRpcMethodDefinition;
import com.tabasoft.jsonrpc.core.model.request.RpcArrayMethodArguments;

import java.util.stream.IntStream;

public class JacksonRpcArrayMethodArguments extends RpcArrayMethodArguments {
    private final ArrayNode arrayNode;

    public JacksonRpcArrayMethodArguments(ArrayNode arrayNode) {
        this.arrayNode = arrayNode;
    }


    @Override
    protected Object[] getArrayArguments(JsonRpcMethodDefinition methodDefinition) {
        final var mapper = new ObjectMapper();
        final var parameterTypes = methodDefinition.getJavaMethod().getParameterTypes();
        return IntStream.range(0, arrayNode.size())
                .mapToObj((index) -> {
                    final var value = arrayNode.get(index);
                    try {
                        return mapper.treeToValue(value, parameterTypes[index]);
                    } catch (JsonProcessingException e) {
                        throw new RpcException(DefaultExceptionMessages.PARSER_ERROR, DefaultExceptionCodes.PARSE_ERROR);
                    }
                })
                .toArray();
    }
}
