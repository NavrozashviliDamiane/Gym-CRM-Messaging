package com.epam.crmgym.dto.trainer;

import com.epam.crmgym.dto.trainer.TrainerDTO;
import com.epam.crmgym.dto.trainer.UpdatedTrainersListResponseDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdatedTrainersListResponseDTOTest {

    @Test
    public void testConstructorAndGetters() {
        List<TrainerDTO> trainersList = new ArrayList<>();
        trainersList.add(new TrainerDTO("username1", "John", "Doe", "Java"));
        trainersList.add(new TrainerDTO("username2", "Jane", "Doe", "Python"));

        UpdatedTrainersListResponseDTO responseDTO = new UpdatedTrainersListResponseDTO(trainersList);

        assertNotNull(responseDTO.getTrainersList());
        assertEquals(2, responseDTO.getTrainersList().size());
        assertEquals("username1", responseDTO.getTrainersList().get(0).getUsername());
        assertEquals("John", responseDTO.getTrainersList().get(0).getFirstName());
        assertEquals("Doe", responseDTO.getTrainersList().get(0).getLastName());
        assertEquals("Java", responseDTO.getTrainersList().get(0).getSpecialization());
        assertEquals("username2", responseDTO.getTrainersList().get(1).getUsername());
        assertEquals("Jane", responseDTO.getTrainersList().get(1).getFirstName());
        assertEquals("Doe", responseDTO.getTrainersList().get(1).getLastName());
        assertEquals("Python", responseDTO.getTrainersList().get(1).getSpecialization());
    }

    @Test
    public void testSetterAndGetters() {
        UpdatedTrainersListResponseDTO responseDTO = new UpdatedTrainersListResponseDTO();

        List<TrainerDTO> trainersList = new ArrayList<>();
        trainersList.add(new TrainerDTO("username1", "John", "Doe", "Java"));
        trainersList.add(new TrainerDTO("username2", "Jane", "Doe", "Python"));

        responseDTO.setTrainersList(trainersList);

        assertNotNull(responseDTO.getTrainersList());
        assertEquals(2, responseDTO.getTrainersList().size());
        assertEquals("username1", responseDTO.getTrainersList().get(0).getUsername());
        assertEquals("John", responseDTO.getTrainersList().get(0).getFirstName());
        assertEquals("Doe", responseDTO.getTrainersList().get(0).getLastName());
        assertEquals("Java", responseDTO.getTrainersList().get(0).getSpecialization());
        assertEquals("username2", responseDTO.getTrainersList().get(1).getUsername());
        assertEquals("Jane", responseDTO.getTrainersList().get(1).getFirstName());
        assertEquals("Doe", responseDTO.getTrainersList().get(1).getLastName());
        assertEquals("Python", responseDTO.getTrainersList().get(1).getSpecialization());
    }
}
