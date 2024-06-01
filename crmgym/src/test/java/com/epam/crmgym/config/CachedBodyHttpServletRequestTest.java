package com.epam.crmgym.config;


import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class CachedBodyHttpServletRequestTest {

    @Test
    public void testGetInputStream() throws IOException {
        // Create a mock HttpServletRequest
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);

        byte[] requestBody = "Test Input Stream".getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }



            @Override
            public void setReadListener(ReadListener listener) {
            }
        };
        when(mockRequest.getInputStream()).thenReturn(servletInputStream);

        CachedBodyHttpServletRequest cachedBodyRequest = new CachedBodyHttpServletRequest(mockRequest);

        ServletInputStream inputStream = cachedBodyRequest.getInputStream();

        byte[] actualBytes = inputStream.readAllBytes();
        assertArrayEquals(requestBody, actualBytes);
    }


}

