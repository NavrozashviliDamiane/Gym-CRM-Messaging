package com.epam.crmgym.util.user;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.exception.UsernameValidationException;
import com.epam.crmgym.repository.UserRepository;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsernameGeneratorTest {

    @Test
    public void testGenerateUsername_ValidNames() throws UsernameValidationException {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByUsername(any())).thenReturn(false);

        UsernameGenerator usernameGenerator = new UsernameGenerator(userRepository);
        String firstName = "John";
        String lastName = "Doe";
        String generatedUsername = usernameGenerator.generateUsername(firstName, lastName);

        assertEquals("john.doe", generatedUsername);
    }

    @Test
    public void testGenerateUsername_FirstNameWithSpecialCharacters() {
        UserRepository userRepository = mock(UserRepository.class);
        UsernameGenerator usernameGenerator = new UsernameGenerator(userRepository);

        String firstName = "John$";
        String lastName = "Doe";

        assertThrows(UsernameValidationException.class, () -> {
            usernameGenerator.generateUsername(firstName, lastName);
        });
    }

    @Test
    public void testGenerateUsername_LastNameWithSpecialCharacters() {
        UserRepository userRepository = mock(UserRepository.class);
        UsernameGenerator usernameGenerator = new UsernameGenerator(userRepository);

        String firstName = "John";
        String lastName = "Doe$";

        assertThrows(UsernameValidationException.class, () -> {
            usernameGenerator.generateUsername(firstName, lastName);
        });
    }


}
