package com.epam.crmgym.service.impl;

import com.epam.crmgym.entity.User;
import com.epam.crmgym.exception.UnauthorizedAccessException;
import com.epam.crmgym.repository.UserRepository;
import com.epam.crmgym.service.AuthenticateService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

    private final UserRepository userRepository;

    public AuthenticateServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean matchUserCredentials(String username, String password) {
        try {
            User user = userRepository.findByUsername(username);
            if (user == null || !user.getPassword().equals(password)) {
                throw new UnauthorizedAccessException("User credentials do not match");
            }
            return true;
        } catch (UnauthorizedAccessException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
