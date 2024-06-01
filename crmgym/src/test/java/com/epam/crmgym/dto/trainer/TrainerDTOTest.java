package com.epam.crmgym.dto.trainer;

import com.epam.crmgym.dto.trainer.TrainerDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainerDTOTest {

    @Test
    public void testConstructorAndGetters() {
        String username = "username";
        String firstName = "John";
        String lastName = "Doe";
        String specialization = "Java";

        TrainerDTO trainerDTO = new TrainerDTO(username, firstName, lastName, specialization);

        assertEquals(username, trainerDTO.getUsername());
        assertEquals(firstName, trainerDTO.getFirstName());
        assertEquals(lastName, trainerDTO.getLastName());
        assertEquals(specialization, trainerDTO.getSpecialization());
    }

    @Test
    public void testSetterAndGetters() {
        TrainerDTO trainerDTO = new TrainerDTO();

        String username = "username";
        String firstName = "John";
        String lastName = "Doe";
        String specialization = "Java";

        trainerDTO.setUsername(username);
        trainerDTO.setFirstName(firstName);
        trainerDTO.setLastName(lastName);
        trainerDTO.setSpecialization(specialization);

        assertEquals(username, trainerDTO.getUsername());
        assertEquals(firstName, trainerDTO.getFirstName());
        assertEquals(lastName, trainerDTO.getLastName());
        assertEquals(specialization, trainerDTO.getSpecialization());
    }
}
