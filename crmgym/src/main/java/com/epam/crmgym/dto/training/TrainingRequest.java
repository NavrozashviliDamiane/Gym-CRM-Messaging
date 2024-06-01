package com.epam.crmgym.dto.training;

import com.epam.crmgym.exception.DateDeSerializer;
import com.epam.crmgym.validation.ValidTrainingDuration;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequest {

    @NotNull(message = "Trainee Username is required")
    @NotBlank(message = "Trainee Username is required")
    private String username;

    @NotNull(message = "Trainer Username is required")
    @NotBlank(message = "Trainer Username is required")
    private String trainerUsername;

    @NotNull(message = "Training name is required")
    @NotBlank(message = "Training name is required")
    private String trainingName;

    @NotNull(message = "Training date is required")
    @Future(message = "Training date must be in the future")
    @JsonDeserialize(using = DateDeSerializer.class)
    private Date trainingDate;

    @ValidTrainingDuration
    @NotNull(message = "Training duration is required")
    private Integer trainingDuration;


}

