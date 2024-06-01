package com.epam.crmgym.config;

import com.epam.crmgym.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testBlacklistToken() {
        String token = "testToken";

        jwtService.blacklistToken(token);

        assertTrue(jwtService.isTokenBlacklisted(token));
    }

    @Test
    void testIsTokenBlacklisted() {
        String token = "testToken";
        jwtService.blacklistToken(token);

        assertTrue(jwtService.isTokenBlacklisted(token));
    }


    @Test
    void testLoadUserByUsername_UserDoesNotExist() {
        String username = "nonExistingUser";
        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> jwtService.loadUserByUsername(username));
    }

    @Test
    void testExtractTokenFromRequest_WithBearerToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer testToken");

        String extractedToken = jwtService.extractTokenFromRequest(request);

        assertEquals("testToken", extractedToken);
    }

    @Test
    void testExtractTokenFromRequest_WithoutBearerToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("InvalidAuthorizationHeader");

        String extractedToken = jwtService.extractTokenFromRequest(request);

        assertNull(extractedToken);
    }


    @Test
    void testAuthenticateUser_IncorrectUsername() {
        String username = "nonExistingUser";
        String password = "password";
        when(userRepository.findByUsername(username)).thenReturn(null);

        assertFalse(jwtService.authenticateUser(username, password));
    }


}
