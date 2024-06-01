package com.epam.crmgym.util.user;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.crmgym.config.JwtService;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class BlockLoginIpHelperTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BlockLoginIpHelper blockLoginIpHelper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateTokenResponse() {
        String username = "testUser";
        User user = new User();
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(jwtService.generateToken(user, new HashMap<>())).thenReturn("testToken");

        ResponseEntity<String> responseEntity = blockLoginIpHelper.generateTokenResponse(username);

        assertNotNull(responseEntity);
        assertEquals("testToken", responseEntity.getBody());
    }




    @Test
    public void testExtractTokenFromRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer testToken");

        String token = blockLoginIpHelper.extractTokenFromRequest(request);

        assertEquals("testToken", token);
    }

    @Test
    public void testGetClientIP() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("X-Forwarded-For")).thenReturn("192.168.0.1, 10.0.0.1");

        String clientIP = blockLoginIpHelper.getClientIP(request);

        assertEquals("192.168.0.1", clientIP);
    }

}
