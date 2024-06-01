package com.epam.crmgym.controller;

import com.epam.crmgym.exception.UnauthorizedAccessException;
import com.epam.crmgym.service.AuthenticateService;
import lombok.extern.slf4j.Slf4j;
import com.epam.crmgym.dto.user.ChangePasswordRequest;
import com.epam.crmgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final AuthenticateService authenticateService;


    private final UserService userService;

    @Autowired
    public UserController(AuthenticateService authenticateService, UserService userService) {
        this.authenticateService = authenticateService;
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<String> changePassword(@Validated @RequestBody ChangePasswordRequest request) throws UnauthorizedAccessException {

        log.info("REST call made to /api/users/change-password endpoint. Request: {}", request);


        if (authenticateService.matchUserCredentials(request.getUsername(), request.getOldPassword())){
            try {
                userService.changePassword(request);
                return ResponseEntity.ok("Password changed successfully");
            } catch (Exception e) {
                log.error("Error occurred while processing /api/users/change-password endpoint.", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.error("Invalid username or password. Authentication failed for user: {}", request.getUsername());
            throw new UnauthorizedAccessException("Authentication failed for user: " + request.getUsername());
        }

    }
}
