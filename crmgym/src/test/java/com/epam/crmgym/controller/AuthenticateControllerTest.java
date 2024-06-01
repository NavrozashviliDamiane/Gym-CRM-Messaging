package com.epam.crmgym.controller;

import com.epam.crmgym.config.JwtService;
import com.epam.crmgym.controller.AuthenticateController;
import com.epam.crmgym.dto.user.LoginRequest;
import com.epam.crmgym.util.user.BlockLoginIpHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticateControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private BlockLoginIpHelper blockLoginIpHelper;

    @InjectMocks
    private AuthenticateController authenticateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void testLogin_TooManyRequests() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        when(blockLoginIpHelper.getClientIP(httpServletRequest)).thenReturn("127.0.0.1");
        when(blockLoginIpHelper.isIPBlocked(any())).thenReturn(true);

        ResponseEntity<String> responseEntity = authenticateController.login(loginRequest, httpServletRequest);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, responseEntity.getStatusCode());

        verify(blockLoginIpHelper, times(2)).getClientIP(httpServletRequest); // Expect getClientIP to be called twice
        verify(blockLoginIpHelper, times(1)).isIPBlocked(any()); // Expect isIPBlocked to be called once
    }



    @Test
    void testLogin_Unsuccessful() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        when(jwtService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(false);
        when(blockLoginIpHelper.getClientIP(httpServletRequest)).thenReturn("127.0.0.1");

        ResponseEntity<String> responseEntity = authenticateController.login(loginRequest, httpServletRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        verify(blockLoginIpHelper, times(2)).getClientIP(httpServletRequest); // Expect getClientIP to be called twice
        verify(jwtService, times(1)).authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
    }


    @Test
    void testLogout_Successful() {
        String token = "validToken";
        when(jwtService.extractTokenFromRequest(httpServletRequest)).thenReturn(token);
        ResponseEntity<String> responseEntity = authenticateController.logout(httpServletRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(jwtService, times(1)).extractTokenFromRequest(httpServletRequest);
        verify(jwtService, times(1)).blacklistToken(token);
    }

    @Test
    void testLogout_InvalidToken() {
        when(jwtService.extractTokenFromRequest(httpServletRequest)).thenReturn(null);
        ResponseEntity<String> responseEntity = authenticateController.logout(httpServletRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        verify(jwtService, times(1)).extractTokenFromRequest(httpServletRequest);
        verify(jwtService, never()).blacklistToken(any());
    }
}
