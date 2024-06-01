package com.epam.crmgym.actuator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DatabaseHealthIndicatorTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private DatabaseHealthIndicator databaseHealthIndicator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHealthIndicatorUp() {
        doNothing().when(jdbcTemplate).execute("SELECT * FROM users");

        Health health = databaseHealthIndicator.health();

        assertEquals(Status.UP, health.getStatus());
        assertEquals("Database is up", health.getDetails().get("message"));

        verify(jdbcTemplate, times(1)).execute("SELECT * FROM users");
    }

    @Test
    public void testHealthIndicatorDown() {
        doThrow(new RuntimeException("Database connection failed")).when(jdbcTemplate).execute("SELECT * FROM users");

        Health health = databaseHealthIndicator.health();

        assertEquals(Status.DOWN, health.getStatus());
        assertEquals("Database connection failed", health.getDetails().get("error"));

        verify(jdbcTemplate, times(1)).execute("SELECT * FROM users");
    }
}
