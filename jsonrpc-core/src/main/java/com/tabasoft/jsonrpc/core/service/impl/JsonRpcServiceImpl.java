package com.tabasoft.jsonrpc.core.service.impl;

import com.tabasoft.jsonrpc.core.model.JsonRpcMethodDefinition;
import com.tabasoft.jsonrpc.core.model.RpcRequestEntity;
import com.tabasoft.jsonrpc.core.model.RpcResponseEntity;
import com.tabasoft.jsonrpc.core.service.JsonRpcHandler;
import com.tabasoft.jsonrpc.core.service.JsonRpcServiceRegistrar;
import com.tabasoft.jsonrpc.schema.annotation.JsonRpcMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class JsonRpcServiceImpl implements JsonRpcServiceRegistrar, JsonRpcHandler {
    private static final Logger logger = LoggerFactory.getLogger(JsonRpcServiceImpl.class);

    private Map<String, JsonRpcMethodDefinition> methods = new HashMap<>();

    @Override
    public void registerService(Object bean) {
        Arrays.stream(bean.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(JsonRpcMethod.class))
                .forEach((method -> {
                    var jsonRpcMethodAnnotationProperties = method.getAnnotation(JsonRpcMethod.class);
                    var nameFromAnnotation = jsonRpcMethodAnnotationProperties.name();
                    var name = nameFromAnnotation.isEmpty() ? method.getName() : nameFromAnnotation;
                    logger.info("Registering method {}", name);
                    // var parameters = Arrays.stream(method.getParameters());
                    var methodDefinition = JsonRpcMethodDefinition
                            .builder()
                            .javaMethod(method)
                            .name(name)
                            .service(bean)
                            .description(jsonRpcMethodAnnotationProperties.description())
                            .build();
                    if (this.methods.containsKey(name)) {
                        // TODO Throw exception
                    }

                    this.methods.put(name, methodDefinition);
                }));
    }

    @Override
    public RpcResponseEntity handleRequest(RpcRequestEntity request) {
        return null;
    }

    @Override
    public Collection<RpcResponseEntity> handleRequest(Collection<RpcRequestEntity> request) {
        return null;
    }
}
