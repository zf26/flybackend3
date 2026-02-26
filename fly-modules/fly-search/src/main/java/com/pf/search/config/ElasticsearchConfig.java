package com.pf.search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Elasticsearch 配置
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.pf.search.repository")
public class ElasticsearchConfig {
    // Spring Boot 会自动配置 ElasticsearchClient
    // 如需自定义配置，可在此添加
}

