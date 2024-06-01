package com.epam.crmgym.service.impl;

import com.epam.crmgym.exception.UnauthorizedAccessException;
import com.epam.crmgym.exception.UsernameValidationException;
import lombok.extern.slf4j.Slf4j;
import com.epam.crmgym.dto.user.ChangePasswordRequest;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.repository.UserRepository;
import com.epam.crmgym.service.AuthenticateService;
import com.epam.crmgym.service.UserService;
import com.epam.crmgym.util.user.PasswordGenerator;
import com.epam.crmgym.util.user.UsernameGenerator;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticateService authenticateService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticateService authenticateService) {
        this.userRepository = userRepository;
        this.authenticateService = authenticateService;
    }




    @Override
    @Transactional
    public User createUser(String firstName, String lastName) throws UsernameValidationException {
        UsernameGenerator usernameGenerator = new UsernameGenerator(userRepository);
        String username = usernameGenerator.generateUsername(firstName, lastName);

        String password = PasswordGenerator.generatePassword(10);
        String salt = PasswordGenerator.generateSalt();
        String hashedPassword = PasswordGenerator.hashPasswordWithSalt(password, salt);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setActive(true);

        User createdUser = userRepository.save(user);

        log.info("User Created Successfully");

        return createdUser;
    }


    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public User saveUser(User user) {

        return userRepository.save(user);
    }


    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) throws UnauthorizedAccessException {
        String transactionId = UUID.randomUUID().toString();
        log.info("Transaction started for trainee creation. Transaction ID: {}", transactionId);

        if (authenticateService.matchUserCredentials(request.getUsername(), request.getOldPassword())) {
            try {
                User user = userRepository.findByUsername(request.getUsername());

                user.setPassword(request.getNewPassword());
                log.info("Transaction finished for setting password. Transaction ID: {}", transactionId);

                userRepository.save(user);
                log.info("Transaction finished for saving user. Transaction ID: {}", transactionId);

                log.info("Password changed successfully for user: {}", request.getUsername());
            } catch (Exception e) {
                log.error("An error occurred during password change for user: {}", request.getUsername(), e);
                throw new ServiceException("Failed to change password for user: " + request.getUsername());
            }
        } else {
            log.error("Authentication failed for user: {}", request.getUsername());
            throw new UnauthorizedAccessException("Authentication failed for user: " + request.getUsername());
        }
    }

}
