package com.tabasoft.jsonrpc.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.jsonrpc")
public class JsonRpcConfigurationProperties {
    private Long maxBatchSize = 10L;
}
