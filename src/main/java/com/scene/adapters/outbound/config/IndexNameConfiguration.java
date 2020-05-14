package com.scene.adapters.outbound.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndexNameConfiguration {

    @Value("${elastic.index.name}")
    private String indexName;

    @Bean
    public String indexName() {
        return indexName;
    }

}
