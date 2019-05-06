package com.tabasoft.jsonrpc.core.model.request.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionCodes;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionMessages;
import com.tabasoft.jsonrpc.core.exception.RpcException;
import com.tabasoft.jsonrpc.core.model.configuration.JsonRpcMethodDefinition;
import com.tabasoft.jsonrpc.core.model.request.RpcObjectMethodArguments;

import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class JacksonRpcObjectMethodArguments extends RpcObjectMethodArguments {
    private final ObjectNode objectNode;

    public JacksonRpcObjectMethodArguments(ObjectNode objectNode) {
        this.objectNode = objectNode;
    }

    @Override
    protected Map<String, Object> getMappedArguments(JsonRpcMethodDefinition methodDefinition) {
        final var mapper = new ObjectMapper();
        final var parameterNames = methodDefinition.getParameterNames();
        final var parameters = methodDefinition.getJavaMethod().getParameters();
        final var nameToTypeMap = IntStream.range(0, parameterNames.length)
                .boxed()
                .collect(Collectors.toMap((index) -> parameterNames[index], (index) -> parameters[index].getType()));
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(objectNode.fieldNames(), Spliterator.ORDERED), false)
                .collect(Collectors.toMap((key) -> key, (value) -> {
                    try {
                        return mapper.treeToValue(objectNode.get(value), nameToTypeMap.get(value));
                    } catch (JsonProcessingException e) {
                        throw new RpcException(DefaultExceptionMessages.PARSER_ERROR, DefaultExceptionCodes.PARSE_ERROR);
                    }
                }));
    }
}

