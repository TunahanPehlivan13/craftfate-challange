package com.craftgate.order.config;

import io.craftgate.Craftgate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CraftgateClientConfig {

    @Value("${craftgate.api-key}")
    private String apiKey;

    @Value("${craftgate.secret-key}")
    private String secretKey;

    @Value("${craftgate.url}")
    private String craftgateUrl;

    @Bean
    public Craftgate craftgate() {
        return new Craftgate(apiKey, secretKey, craftgateUrl);
    }
}
