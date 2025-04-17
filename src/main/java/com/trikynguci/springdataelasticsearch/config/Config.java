package com.trikynguci.springdataelasticsearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.trikynguci.springdataelasticsearch.repository")
@ComponentScan(basePackages = {"com.trikynguci.springdataelasticsearch"})
public class Config extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    public String elasticsearchUrl;

    @Bean
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
    }
}
