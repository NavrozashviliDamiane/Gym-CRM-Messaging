package com.epam.crmgym.dto.trainer;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainer.TrainerRegistrationResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainerRegistrationResponseTest {

    @Test
    public void testConstructorAndGetters() {
        String username = "username";
        String password = "password";

        TrainerRegistrationResponse response = new TrainerRegistrationResponse(username, password);

        assertEquals(username, response.getUsername());
        assertEquals(password, response.getPassword());
    }

    @Test
    public void testSetterAndGetters() {
        TrainerRegistrationResponse response = new TrainerRegistrationResponse();

        String username = "username";
        String password = "password";

        response.setUsername(username);
        response.setPassword(password);

        assertEquals(username, response.getUsername());
        assertEquals(password, response.getPassword());
    }
}
