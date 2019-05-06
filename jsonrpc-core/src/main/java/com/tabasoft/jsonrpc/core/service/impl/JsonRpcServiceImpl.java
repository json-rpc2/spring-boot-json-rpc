package com.tabasoft.jsonrpc.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionCodes;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionMessages;
import com.tabasoft.jsonrpc.core.exception.RpcException;
import com.tabasoft.jsonrpc.core.model.response.RpcResponseEntity;
import com.tabasoft.jsonrpc.core.service.JsonRpcContext;
import com.tabasoft.jsonrpc.core.service.JsonRpcHandler;
import com.tabasoft.jsonrpc.core.service.RpcMethodInvoker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class JsonRpcServiceImpl implements JsonRpcHandler {
    private static final Logger logger = LoggerFactory.getLogger(JsonRpcServiceImpl.class);

    private final JsonRpcContext rpcContext;
    private final RpcMethodInvoker rpcMethodInvoker;

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
            return handleBatchRequest(((ArrayNode) node));
        }
    }

    private RpcResponseEntity invokeSingleMethod(ObjectNode node) {
        var paramsFactory = new JacksonRpcParameterFactory();
        var parameters = paramsFactory.makeParameters(node);

        var method = rpcContext.getMethodByName(parameters.getMethodName());
        return rpcMethodInvoker.invokeMethod(method, parameters);
    }

    private String handleBatchRequest(ArrayNode node) {
        var responses = new ArrayList<String>();
        node.forEach((requestNode) -> {
            try {
                responses.add(handleRequest(requestNode));
            } catch (JsonProcessingException e) {
                    e.printStackTrace();
            }
        });

        return "[" + String.join( ",", responses) + "]";
    }


}
