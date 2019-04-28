package com.tabasoft.jsonrpc.http.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.jsonrpc.http")
public class JsonRpcHttpConfigurationProperties {
    private String endpointUrl = "/api";
}
