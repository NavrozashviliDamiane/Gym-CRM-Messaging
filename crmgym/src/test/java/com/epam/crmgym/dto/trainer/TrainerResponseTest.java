package com.epam.crmgym.dto.trainer;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainer.TrainerResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainerResponseTest {

    @Test
    public void testConstructorAndGetters() {
        String username = "username";
        String firstName = "John";
        String lastName = "Doe";
        String specialization = "Java";

        TrainerResponse response = new TrainerResponse(username, firstName, lastName, specialization);

        assertEquals(username, response.getUsername());
        assertEquals(firstName, response.getFirstName());
        assertEquals(lastName, response.getLastName());
        assertEquals(specialization, response.getSpecialization());
    }

    @Test
    public void testSetterAndGetters() {
        TrainerResponse response = new TrainerResponse();

        String username = "username";
        String firstName = "John";
        String lastName = "Doe";
        String specialization = "Java";

        response.setUsername(username);
        response.setFirstName(firstName);
        response.setLastName(lastName);
        response.setSpecialization(specialization);

        assertEquals(username, response.getUsername());
        assertEquals(firstName, response.getFirstName());
        assertEquals(lastName, response.getLastName());
        assertEquals(specialization, response.getSpecialization());
    }
}
