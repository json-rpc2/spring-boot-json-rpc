package com.tabasoft.jsonrpc.autoconfigure;

import com.tabasoft.jsonrpc.core.service.JsonRpcContext;
import com.tabasoft.jsonrpc.schema.annotation.JsonRpcService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


@RequiredArgsConstructor
public class JsonRpcServiceBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JsonRpcServiceBeanPostProcessor.class);
    private final JsonRpcContext jsonRpcContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(JsonRpcService.class)) {
            jsonRpcContext.registerService(bean);
            logger.info("Registering bean {} as JSON RPC Service", beanName);
        }
        return bean;
    }
}
