package com.epam.crmgym.config;


import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class AppConfigTest {



    @Test
    public void testUnsuccessfulLoginAttemptsBean() {
        AppConfig appConfig = new AppConfig();
        Map<String, Integer> unsuccessfulLoginAttempts = appConfig.unsuccessfulLoginAttempts();

        assertTrue(unsuccessfulLoginAttempts instanceof ConcurrentHashMap);
    }

    @Test
    public void testBlockedIPsBean() {
        AppConfig appConfig = new AppConfig();
        Map<String, Long> blockedIPs = appConfig.blockedIPs();

        assertTrue(blockedIPs instanceof ConcurrentHashMap);
    }
}
