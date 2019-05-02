package com.tabasoft.jsonrpc.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionCodes;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionMessages;
import com.tabasoft.jsonrpc.core.exception.RpcException;
import com.tabasoft.jsonrpc.core.model.JsonRpcMethodDefinition;

import java.util.ArrayList;


/**
 * Utility class for working with RPC parameters
 */
public final class RpcParameterUtils {

    private RpcParameterUtils() {

    }

    /**
     * Get RPC method parameters as array in sequence which must be same as java method's parameters sequence
     * and conform types
     * @param parametersNode json node 'params' from RPC request
     * @param methodDefinition definition of JSON RPC method. Must contain {@link JsonRpcMethodDefinition#getParameterNames()}
     * @return RPC Parameters
     */
    public static Object[] getRpcParameters(JsonNode parametersNode, JsonRpcMethodDefinition methodDefinition) {
        var mapper = new ObjectMapper();
        var methodParameters = methodDefinition.getJavaMethod().getParameters();
        var parameterValues = new ArrayList<Object>();
        if (methodParameters.length != parametersNode.size()) {
            throw new RpcException(DefaultExceptionMessages.INVALID_PARAMS, DefaultExceptionCodes.INVALID_PARAMS);
        }
        var parameterNames = methodDefinition.getParameterNames();
        for (var i = 0; i < methodParameters.length; i++) {
            var methodParameter = methodParameters[i];
            var parameterJsonNode = getParameterJsonNode(parametersNode, parameterNames[i], i);
            try {
                parameterValues.add(mapper.treeToValue(parameterJsonNode, methodParameter.getType()));
            } catch (JsonProcessingException e) {
                throw new RpcException(DefaultExceptionMessages.PARSER_ERROR, DefaultExceptionCodes.PARSE_ERROR);
            }
        }

        return parameterValues.toArray();
    }

    private static JsonNode getParameterJsonNode(JsonNode parametersNode, String parameterName, int index) {
        if (parametersNode.isArray()) {
            return parametersNode.get(index);
        } else {
            return parametersNode.get(parameterName);
        }
    }
}
