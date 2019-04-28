package com.tabasoft.jsonrpc.http.autoconfigure;

import com.tabasoft.jsonrpc.autoconfigure.JsonRpcAutoConfiguration;
import com.tabasoft.jsonrpc.core.service.JsonRpcHandler;
import com.tabasoft.jsonrpc.http.RpcHttpServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;

@Configuration
@EnableConfigurationProperties(JsonRpcHttpConfigurationProperties.class)
@ComponentScan("com.tabasoft.jsonrpc.http.*")
@AutoConfigureBefore(JsonRpcAutoConfiguration.class)
@RequiredArgsConstructor
public class JsonRpcHttpAutoconfigure {
    private final JsonRpcHttpConfigurationProperties properties;

    @Bean
    public ServletRegistrationBean<HttpServlet> rpcEndpointServlet(JsonRpcHandler handler) {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new RpcHttpServlet(handler));
        servRegBean.addUrlMappings(properties.getEndpointUrl());
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }
}
