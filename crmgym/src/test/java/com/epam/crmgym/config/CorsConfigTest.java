package com.epam.crmgym.config;


import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.filter.CorsFilter;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CorsConfigTest {

    @Test
    public void testCorsFilterConfiguration() throws Exception {
        CorsConfig corsConfig = new CorsConfig();

        // Get the CorsFilter bean
        CorsFilter corsFilter = corsConfig.corsFilter();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setRequestURI("/example");
        request.addHeader("Origin", "http://localhost:8080");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain filterChain = new MockFilterChain();

        corsFilter.doFilter(request, response, filterChain);

        Collection<String> headerNames = response.getHeaderNames();
        System.out.println("Response Headers:");
        for (String headerName : headerNames) {
            System.out.println(headerName + ": " + response.getHeader(headerName));
        }

        assertEquals("http://localhost:8080", response.getHeader("Access-Control-Allow-Origin"));
        assertEquals("true", response.getHeader("Access-Control-Allow-Credentials"));

        String allowHeaders = response.getHeader("Access-Control-Allow-Headers");
        if (allowHeaders != null) {
            assertTrue(allowHeaders.contains("*"));
        } else {
            System.out.println("Access-Control-Allow-Headers header is not present in the response.");
        }

        String allowMethods = response.getHeader("Access-Control-Allow-Methods");
        if (allowMethods != null) {
            assertTrue(allowMethods.contains("GET"));
        } else {
            System.out.println("Access-Control-Allow-Methods header is not present in the response.");
        }
    }
}