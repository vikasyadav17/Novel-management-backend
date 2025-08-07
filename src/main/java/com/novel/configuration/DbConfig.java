package com.novel.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.sql.Connection;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.driver}")
    private String driverclassString;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().url(dbUrl).username(username).password(password)
                .driverClassName(driverclassString).build();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testConnection() {
        try (Connection connection = dataSource().getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Successfully connected to MYSQL DB");
            }

        } catch (Exception exc) {
            System.err.println("Failed to get the conneciton");
            exc.printStackTrace();
        }
    }

}
