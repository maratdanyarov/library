package com.danyarov.library.config;

import com.danyarov.library.dao.ConnectionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Database configuration
 */
@Configuration
public class DatabaseConfig {

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String dbPassword;

    @Value("${db.pool.initial-size:5}")
    private int initialPoolSize;

    @Value("${db.pool.max-size:20}")
    private int maxPoolSize;

    private ConnectionPool connectionPool;

    @PostConstruct
    public void init() {
        connectionPool = ConnectionPool.getInstance(
                dbUrl, dbUsername, dbPassword, initialPoolSize, maxPoolSize
        );
    }

    @PreDestroy
    public void destroy() {
        if (connectionPool != null) {
            connectionPool.shutdown();
        }
    }

    @Bean
    public ConnectionPool connectionPool() {
        return connectionPool;
    }
}
