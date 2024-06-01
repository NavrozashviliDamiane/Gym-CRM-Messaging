package com.epam.crmgym.util.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordGeneratorTest {

    @Test
    public void testGeneratePassword() {
        int length = 8;
        String password = PasswordGenerator.generatePassword(length);
        assertEquals(length, password.length());
    }


    @Test
    public void testHashPasswordWithSalt() {
        String password = "testPassword";
        String salt = PasswordGenerator.generateSalt();
        String hashedPassword = PasswordGenerator.hashPasswordWithSalt(password, salt);

        assertNotEquals(password, hashedPassword);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        assertTrue(passwordEncoder.matches(password + salt, hashedPassword));
    }
}
