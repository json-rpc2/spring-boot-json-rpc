package com.tabasoft.jsonrpc.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jsonrpc")
@Data
public class JsonRpcConfigurationProperties {
    private Long maxBatchSize = 10L;
}
