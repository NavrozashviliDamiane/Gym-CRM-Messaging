package com.epam.crmgym.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignClientInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
                logger.info("Added Authorization header with token: Bearer {}", token);
            } else {
                logger.warn("JWT token is null.");
            }
        } else {
            logger.warn("Security context authentication is null.");
        }
    }
}
