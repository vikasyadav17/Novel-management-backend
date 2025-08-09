package com.novel.web.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class DbConfigTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void whenApplicationStarts_thenDataSourceBeanShouldNotBeNull() {
        log.info("🔍 Checking if DataSource bean is loaded...");
        // assertThat(dataSource).isNotNull();
        assertNotEquals(null, dataSource);
        log.info("✅ DataSource bean is available.");
    }

    @Test
    void whenApplicationStarts_thenDbConnectionIsSuccessful() throws Exception {
        log.info("🔍 Trying to connect to the database...");
        try (Connection conn = dataSource.getConnection()) {
            boolean valid = conn.isValid(2);
            log.info("DB Connection valid: {}", valid);
            assertTrue(valid);
        } catch (Exception e) {
            log.error("Failed to connect to the database", e);
            throw e; // rethrow so test fails
        }
    }
}
