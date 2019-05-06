package com.tabasoft.jsonrpc.autoconfigure;

import com.tabasoft.jsonrpc.core.service.JsonRpcContext;
import com.tabasoft.jsonrpc.core.service.JsonRpcHandler;
import com.tabasoft.jsonrpc.core.service.RpcMethodInvoker;
import com.tabasoft.jsonrpc.core.service.impl.JsonRpcContextImpl;
import com.tabasoft.jsonrpc.core.service.impl.JsonRpcServiceImpl;
import com.tabasoft.jsonrpc.core.service.impl.RpcMethodInvokerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(JsonRpcConfigurationProperties.class)
public class JsonRpcAutoConfiguration implements BeanFactoryPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JsonRpcAutoConfiguration.class);

    @Bean
    JsonRpcServiceBeanPostProcessor jsonRpcServiceBeanPostProcessor() {
        return new JsonRpcServiceBeanPostProcessor(jsonRpcContext());
    }

    @Bean
    @ConditionalOnMissingBean
    JsonRpcHandler jsonRpcHandlerService() {
        return new JsonRpcServiceImpl(jsonRpcContext(), rpcMethodInvoker());
    }

    @Bean
    @ConditionalOnMissingBean
    JsonRpcContext jsonRpcContext() {
        return new JsonRpcContextImpl();
    }


    @Bean
    @ConditionalOnMissingBean
    RpcMethodInvoker rpcMethodInvoker() {
        return new RpcMethodInvokerImpl();
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        logger.debug("Registering JSON RPC Bean Post Processor");
        configurableListableBeanFactory.addBeanPostProcessor(jsonRpcServiceBeanPostProcessor());
    }
}
