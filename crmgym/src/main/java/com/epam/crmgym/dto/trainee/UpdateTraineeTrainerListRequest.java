package com.epam.crmgym.dto.trainee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTraineeTrainerListRequest {
    private String traineeUsername;
    private String traineePassword;
    private List<String> trainerUsernames;

}

