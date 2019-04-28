package com.tabasoft.jsonrpc.autoconfigure;

import com.tabasoft.jsonrpc.core.service.JsonRpcServiceRegistrar;
import com.tabasoft.jsonrpc.core.service.impl.JsonRpcServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(JsonRpcConfigurationProperties.class)
public class JsonRpcAutoConfiguration implements BeanFactoryPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JsonRpcAutoConfiguration.class);

    @Bean
    JsonRpcServiceBeanPostProcessor jsonRpcServiceBeanPostProcessor() {
        return new JsonRpcServiceBeanPostProcessor(jsonRpcHandlerService());
    }

    @Bean
    JsonRpcServiceRegistrar jsonRpcHandlerService() {
        return new JsonRpcServiceImpl();
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        logger.debug("Registering JSON RPC Bean Post Processor");
        configurableListableBeanFactory.addBeanPostProcessor(jsonRpcServiceBeanPostProcessor());
    }
}
