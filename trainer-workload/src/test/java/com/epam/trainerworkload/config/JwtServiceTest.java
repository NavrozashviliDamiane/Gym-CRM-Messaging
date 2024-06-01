package com.epam.trainerworkload.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private String secretKey = "dGhpcyBpcyBteSBzZWN1cmUga2V5IGFuZCB5b3UgY2Fubm90IGhhY2sgaXQ="; // Base64 encoded key

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
    }

    @Test
    void extractAllClaims_validToken_success() {
        String token = generateTestToken("testuser");
        Claims claims = jwtService.extractAllClaims(token);

        assertNotNull(claims);
        assertEquals("testuser", claims.getSubject());
    }

    @Test
    void extractUsername_validToken_success() {
        String token = generateTestToken("testuser");
        String username = jwtService.extractUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    void validateToken_validToken_success() {
        String token = generateTestToken("testuser");
        assertTrue(jwtService.validateToken(token));
    }

    @Test
    void validateToken_invalidToken_failure() {
        String invalidToken = "invalidToken";
        assertFalse(jwtService.validateToken(invalidToken));
    }

    private String generateTestToken(String username) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(key)
                .compact();
    }
}
