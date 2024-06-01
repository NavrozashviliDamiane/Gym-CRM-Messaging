package com.epam.crmgym.dto.trainee;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainee.TraineeProfileDTO;
import com.epam.crmgym.dto.trainer.TrainerDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TraineeProfileDTOTest {

    @Test
    public void testConstructorAndGetters() {
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date();
        String address = "123 Main St";
        boolean active = true;
        List<TrainerDTO> trainers = new ArrayList<>();

        TraineeProfileDTO traineeProfileDTO = new TraineeProfileDTO(firstName, lastName, dateOfBirth, address, active, trainers);

        assertEquals(firstName, traineeProfileDTO.getFirstName());
        assertEquals(lastName, traineeProfileDTO.getLastName());
        assertEquals(dateOfBirth, traineeProfileDTO.getDateOfBirth());
        assertEquals(address, traineeProfileDTO.getAddress());
        assertEquals(active, traineeProfileDTO.isActive());
        assertEquals(trainers, traineeProfileDTO.getTrainers());
    }

    @Test
    public void testSetterAndGetters() {
        TraineeProfileDTO traineeProfileDTO = new TraineeProfileDTO();

        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date();
        String address = "123 Main St";
        boolean active = true;
        List<TrainerDTO> trainers = new ArrayList<>();

        traineeProfileDTO.setFirstName(firstName);
        traineeProfileDTO.setLastName(lastName);
        traineeProfileDTO.setDateOfBirth(dateOfBirth);
        traineeProfileDTO.setAddress(address);
        traineeProfileDTO.setActive(active);
        traineeProfileDTO.setTrainers(trainers);

        assertEquals(firstName, traineeProfileDTO.getFirstName());
        assertEquals(lastName, traineeProfileDTO.getLastName());
        assertEquals(dateOfBirth, traineeProfileDTO.getDateOfBirth());
        assertEquals(address, traineeProfileDTO.getAddress());
        assertEquals(active, traineeProfileDTO.isActive());
        assertEquals(trainers, traineeProfileDTO.getTrainers());
    }

    @Test
    public void testEqualsAndHashCode() {
        TraineeProfileDTO traineeProfileDTO1 = new TraineeProfileDTO("John", "Doe", new Date(), "123 Main St", true, new ArrayList<>());
        TraineeProfileDTO traineeProfileDTO2 = new TraineeProfileDTO("John", "Doe", new Date(), "123 Main St", true, new ArrayList<>());
        TraineeProfileDTO traineeProfileDTO3 = new TraineeProfileDTO("Jane", "Smith", new Date(), "456 Elm St", false, new ArrayList<>());

        assertEquals(traineeProfileDTO1, traineeProfileDTO2);
        assertEquals(traineeProfileDTO1.hashCode(), traineeProfileDTO2.hashCode());
        assertNotEquals(traineeProfileDTO1, traineeProfileDTO3);
    }

    @Test
    public void testToString() {
        TraineeProfileDTO traineeProfileDTO = new TraineeProfileDTO("John", "Doe", new Date(), "123 Main St", true, new ArrayList<>());
        assertEquals("TraineeProfileDTO(firstName=John, lastName=Doe, dateOfBirth=" + traineeProfileDTO.getDateOfBirth() +
                ", address=123 Main St, active=true, trainers=[])", traineeProfileDTO.toString());
    }
}
