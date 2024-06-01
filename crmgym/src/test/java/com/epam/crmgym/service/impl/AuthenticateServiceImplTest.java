package com.epam.crmgym.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.entity.User;
import com.epam.crmgym.exception.UnauthorizedAccessException;
import com.epam.crmgym.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticateServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticateServiceImpl authenticateService;

    @Test
    void matchUserCredentials_ValidCredentials_ReturnsTrue() {
        String username = "testUser";
        String password = "testPassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        when(userRepository.findByUsername(username)).thenReturn(user);

        boolean result = authenticateService.matchUserCredentials(username, password);

        assertTrue(result);
    }

    @Test
    void matchUserCredentials_InvalidUsername_ReturnsFalse() {
        String username = "nonExistingUser";
        String password = "testPassword";
        when(userRepository.findByUsername(username)).thenReturn(null);

        boolean result = authenticateService.matchUserCredentials(username, password);

        assertFalse(result);
    }

    @Test
    void matchUserCredentials_InvalidPassword_ReturnsFalse() {
        String username = "testUser";
        String password = "wrongPassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword("correctPassword");
        when(userRepository.findByUsername(username)).thenReturn(user);

        boolean result = authenticateService.matchUserCredentials(username, password);

        assertFalse(result);
    }

    @Test
    void matchUserCredentials_NullUser_ReturnsFalse() {
        String username = "testUser";
        String password = "testPassword";
        when(userRepository.findByUsername(username)).thenReturn(null);

        boolean result = authenticateService.matchUserCredentials(username, password);

        assertFalse(result);
    }


}
