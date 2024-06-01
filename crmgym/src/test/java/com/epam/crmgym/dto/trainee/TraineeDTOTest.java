package com.epam.crmgym.dto.trainee;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainee.TraineeDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TraineeDTOTest {

    @Test
    public void testConstructorAndGetters() {
        String username = "testUsername";
        String firstName = "John";
        String lastName = "Doe";

        TraineeDTO traineeDTO = new TraineeDTO(username, firstName, lastName);

        assertEquals(username, traineeDTO.getUsername());
        assertEquals(firstName, traineeDTO.getFirstName());
        assertEquals(lastName, traineeDTO.getLastName());
    }

    @Test
    public void testSetterAndGetters() {
        TraineeDTO traineeDTO = new TraineeDTO();

        String username = "testUsername";
        String firstName = "John";
        String lastName = "Doe";

        traineeDTO.setUsername(username);
        traineeDTO.setFirstName(firstName);
        traineeDTO.setLastName(lastName);

        assertEquals(username, traineeDTO.getUsername());
        assertEquals(firstName, traineeDTO.getFirstName());
        assertEquals(lastName, traineeDTO.getLastName());
    }

    @Test
    public void testEqualsAndHashCode() {
        TraineeDTO traineeDTO1 = new TraineeDTO("testUsername", "John", "Doe");
        TraineeDTO traineeDTO2 = new TraineeDTO("testUsername", "John", "Doe");
        TraineeDTO traineeDTO3 = new TraineeDTO("differentUsername", "Jane", "Smith");

        assertEquals(traineeDTO1, traineeDTO2);
        assertEquals(traineeDTO1.hashCode(), traineeDTO2.hashCode());
        assertNotEquals(traineeDTO1, traineeDTO3);
    }

    @Test
    public void testToString() {
        TraineeDTO traineeDTO = new TraineeDTO("testUsername", "John", "Doe");
        assertEquals("TraineeDTO(username=testUsername, firstName=John, lastName=Doe)", traineeDTO.toString());
    }
}
