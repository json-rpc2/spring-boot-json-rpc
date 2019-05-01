package com.tabasoft.jsonrpc.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionCodes;
import com.tabasoft.jsonrpc.core.exception.DefaultExceptionMessages;
import com.tabasoft.jsonrpc.core.exception.RpcException;
import com.tabasoft.jsonrpc.core.exception.RpcInitializationException;
import com.tabasoft.jsonrpc.core.model.JsonRpcMethodDefinition;
import com.tabasoft.jsonrpc.core.model.JsonRpcParameter;
import com.tabasoft.jsonrpc.core.service.JsonRpcContext;
import com.tabasoft.jsonrpc.schema.annotation.JsonRpcMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonRpcContextImpl implements JsonRpcContext {
    private static final Logger logger = LoggerFactory.getLogger(JsonRpcContextImpl.class);

    private Map<String, JsonRpcMethodDefinition> methods = new HashMap<>();

    @Override
    public void registerService(Object bean) {
        Arrays.stream(bean.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(JsonRpcMethod.class))
                .forEach((method -> registerMethod(bean, method)));
    }

    @Override
    public JsonRpcMethodDefinition getMethodByName(String name) {
        if (!methods.containsKey(name)) {
            throw new RpcException(DefaultExceptionMessages.METHOD_NOT_FOUND, DefaultExceptionCodes.METHOD_NOT_FOUND);
        }
        return methods.get(name);
    }

    private void registerMethod(Object bean, Method method) {
        var jsonRpcMethodAnnotationProperties = method.getAnnotation(JsonRpcMethod.class);
        var nameFromAnnotation = jsonRpcMethodAnnotationProperties.name();
        var name = nameFromAnnotation.isEmpty() ? method.getName() : nameFromAnnotation;
        logger.debug("Registering method {}", name);
        var parameters = Arrays.stream(method.getParameters()).map(this::toJsonRpcParameter).collect(Collectors.toList());
        var discoverer = new DefaultParameterNameDiscoverer();
        var methodDefinition = JsonRpcMethodDefinition
                .builder()
                .javaMethod(method)
                .name(name)
                .service(bean)
                .parameters(parameters)
                .description(jsonRpcMethodAnnotationProperties.description())
                .parameterNames(discoverer.getParameterNames(method))
                .build();
        if (this.methods.containsKey(name)) {
            throw new RpcInitializationException(String.format("Detected duplicated method name %s", name));
        }

        this.methods.put(name, methodDefinition);
    }

    private JsonRpcParameter toJsonRpcParameter(Parameter parameter) {
        var objectMapper = new ObjectMapper();
        var schemaGenerator = new JsonSchemaGenerator(objectMapper);
        try {
            var schema = schemaGenerator.generateSchema(parameter.getType());
            return JsonRpcParameter.builder()
                    .name(parameter.getName())
                    .type(parameter.getType().getCanonicalName())
                    .schema(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RpcInitializationException(String.format("Can not build JSON Schema for parameter %s", parameter.getName()), e);
        }
    }
}
