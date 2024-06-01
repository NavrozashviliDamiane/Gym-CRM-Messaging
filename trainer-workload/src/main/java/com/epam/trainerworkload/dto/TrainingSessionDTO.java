package com.epam.trainerworkload.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSessionDTO {

    @NotNull(message = "Trainer username is required")
    @NotBlank(message = "Trainer username cannot be blank")
    private String trainerUsername;

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    private boolean isActive;

    @NotNull(message = "Training date is required")
    @FutureOrPresent(message = "Training date must be in the present or future")
    private Date trainingDate;

    @NotNull(message = "Training duration is required")
    @Min(value = 1, message = "Training duration must be at least 1 minute")
    private Integer trainingDuration;

    @NotNull(message = "Action type is required")
    @Pattern(regexp = "ADD|DELETE", message = "Action type must be either 'ADD' or 'DELETE'")
    private String actionType;
}
