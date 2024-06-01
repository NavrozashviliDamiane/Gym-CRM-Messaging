package com.epam.crmgym.service.impl;

import static org.junit.jupiter.api.Assertions.*;


import com.epam.crmgym.dto.user.ChangePasswordRequest;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.exception.UnauthorizedAccessException;
import com.epam.crmgym.exception.UsernameValidationException;
import com.epam.crmgym.repository.UserRepository;
import com.epam.crmgym.service.AuthenticateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticateService authenticateService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_ValidInput_ReturnsCreatedUser() throws UsernameValidationException {
        String firstName = "John";
        String lastName = "Doe";
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername("john.doe");
        when(userRepository.save(any())).thenReturn(user);

        User createdUser = userService.createUser(firstName, lastName);

        assertNotNull(createdUser);
        assertEquals(firstName, createdUser.getFirstName());
        assertEquals(lastName, createdUser.getLastName());
        assertEquals("john.doe", createdUser.getUsername());
    }

    @Test
    void deleteUserById_ValidUserId_DeletesUser() {
        Long userId = 1L;

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void saveUser_ValidUser_ReturnsSavedUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);
        assertNotNull(savedUser);
        assertEquals(user, savedUser);
    }

    @Test
    void changePassword_ValidRequest_PasswordChanged() throws UnauthorizedAccessException {
        ChangePasswordRequest request = new ChangePasswordRequest("username", "oldPassword", "newPassword");
        User user = new User();
        user.setUsername("username");
        user.setPassword("oldPassword");
        when(authenticateService.matchUserCredentials("username", "oldPassword")).thenReturn(true);
        when(userRepository.findByUsername("username")).thenReturn(user);

        assertDoesNotThrow(() -> userService.changePassword(request));

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changePassword_InvalidCredentials_ThrowsUnauthorizedAccessException() {
        ChangePasswordRequest request = new ChangePasswordRequest("username", "oldPassword", "newPassword");
        when(authenticateService.matchUserCredentials("username", "oldPassword")).thenReturn(false);

        assertThrows(UnauthorizedAccessException.class, () -> userService.changePassword(request));
    }
}
