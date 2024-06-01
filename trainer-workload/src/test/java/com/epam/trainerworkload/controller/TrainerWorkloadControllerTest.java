package com.epam.trainerworkload.controller;

import com.epam.trainerworkload.dto.GetTotalTrainingHoursRequestDTO;
import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.service.TrainerWorkloadService;
import com.epam.trainerworkload.util.transaction.TransactionIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TrainerWorkloadControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrainerWorkloadService service;

    @InjectMocks
    private TrainerWorkloadController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void manageTrainingSession_validRequest_success() throws Exception {
        TrainingSessionDTO dto = new TrainingSessionDTO();
        doNothing().when(service).manageTrainingSession(any(TrainingSessionDTO.class));

        mockMvc.perform(post("/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"trainerUsername\":\"john_doe\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"isActive\":true,\"trainingDate\":\"2024-12-01T00:00:00.000+00:00\",\"trainingDuration\":60,\"actionType\":\"ADD\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Session processed successfully"));

        verify(service).manageTrainingSession(any(TrainingSessionDTO.class));
    }

    @Test
    void getTotalTrainingHours_validRequest_success() throws Exception {
        GetTotalTrainingHoursRequestDTO requestDTO = new GetTotalTrainingHoursRequestDTO();
        requestDTO.setUsername("testUser");
        requestDTO.setYear(2024);
        requestDTO.setMonth(5);
        when(service.getTotalTrainingHours(any(GetTotalTrainingHoursRequestDTO.class))).thenReturn(100);

        mockMvc.perform(get("/workload/total-hours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"year\":2024,\"month\":5}"))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));

        verify(service).getTotalTrainingHours(any(GetTotalTrainingHoursRequestDTO.class));
    }
}
