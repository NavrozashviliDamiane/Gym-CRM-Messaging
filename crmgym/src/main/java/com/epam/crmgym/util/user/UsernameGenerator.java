package com.epam.crmgym.util.user;


import com.epam.crmgym.exception.UsernameValidationException;
import com.epam.crmgym.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UsernameGenerator {

    private final UserRepository userRepository;

    public UsernameGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateUsername(String firstName, String lastName) throws UsernameValidationException {
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String username = baseUsername;
        int suffix = 1;

        while (usernameAlreadyExists(username)) {
            username = baseUsername + suffix;
            suffix++;
        }

        List<String> validationErrors = new ArrayList<>();
        validateUsername(username, validationErrors);

        if (!validationErrors.isEmpty()) {
            throw new UsernameValidationException(String.join("; ", validationErrors));
        }

        return username;
    }

    private boolean usernameAlreadyExists(String username) {
        return userRepository.existsByUsername(username);
    }

    private void validateUsername(String username, List<String> errors) throws UsernameValidationException {
        if (username.length() < 6 || username.length() > 32) {
            errors.add("Username must be between 6 and 32 characters long");
        }

        if (!username.matches("[a-zA-Z0-9._-]+")) {
            errors.add("Username must contain only letters, numbers, dots, underscores, or hyphens");
        }

        if (username.contains(" ")) {
            errors.add("Username cannot contain empty spaces");
        }

        if (!errors.isEmpty()) {
            throw new UsernameValidationException(String.join("; ", errors));
        }
    }
}