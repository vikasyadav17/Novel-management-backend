package com.novel.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

import javax.sql.DataSource;

@Slf4j
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
        log.info("Bean declared for data source");
        return DataSourceBuilder.create().url(dbUrl).username(username).password(password)
                .driverClassName(driverclassString).build();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testConnection() {
        try (Connection connection = dataSource().getConnection()) {
            if (connection != null && !connection.isClosed()) {
                log.info("Connected to the database");

            }

        } catch (Exception exc) {
            log.error("connection  to the database is unsuccessful");
            exc.printStackTrace();
        }
    }

}
