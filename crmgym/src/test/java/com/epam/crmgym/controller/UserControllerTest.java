package com.epam.crmgym.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.user.ChangePasswordRequest;
import com.epam.crmgym.exception.UnauthorizedAccessException;
import com.epam.crmgym.service.AuthenticateService;
import com.epam.crmgym.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private AuthenticateService authenticateService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testChangePassword_Successful() throws UnauthorizedAccessException {
        ChangePasswordRequest request = new ChangePasswordRequest("username", "oldPassword", "newPassword");
        when(authenticateService.matchUserCredentials(request.getUsername(), request.getOldPassword())).thenReturn(true);

        ResponseEntity<String> responseEntity = userController.changePassword(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Password changed successfully", responseEntity.getBody());
        verify(authenticateService, times(1)).matchUserCredentials(request.getUsername(), request.getOldPassword());
        verify(userService, times(1)).changePassword(request);
    }

    @Test
    void testChangePassword_UnauthorizedAccessException() throws UnauthorizedAccessException {
        ChangePasswordRequest request = new ChangePasswordRequest("username", "oldPassword", "newPassword");
        when(authenticateService.matchUserCredentials(request.getUsername(), request.getOldPassword())).thenReturn(false);

        UnauthorizedAccessException exception = assertThrows(UnauthorizedAccessException.class, () -> userController.changePassword(request));
        assertEquals("Authentication failed for user: username", exception.getMessage());
        verify(authenticateService, times(1)).matchUserCredentials(request.getUsername(), request.getOldPassword());
        verify(userService, never()).changePassword(request);
    }

    @Test
    void testChangePassword_InternalServerError() throws UnauthorizedAccessException {
        ChangePasswordRequest request = new ChangePasswordRequest("username", "oldPassword", "newPassword");
        Errors errors = new BeanPropertyBindingResult(request, "request");
        when(authenticateService.matchUserCredentials(request.getUsername(), request.getOldPassword())).thenReturn(true);
        doThrow(new RuntimeException()).when(userService).changePassword(request);

        ResponseEntity<String> responseEntity = userController.changePassword(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(authenticateService, times(1)).matchUserCredentials(request.getUsername(), request.getOldPassword());
        verify(userService, times(1)).changePassword(request);
    }
}