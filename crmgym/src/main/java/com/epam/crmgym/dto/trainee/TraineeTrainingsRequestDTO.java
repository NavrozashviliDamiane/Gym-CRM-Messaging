package com.epam.crmgym.dto.trainee;

import com.epam.crmgym.exception.DateDeSerializer;
import com.epam.crmgym.validation.DateRange;
import com.epam.crmgym.validation.ValidTrainingType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DateRange(message = "From date must be before or equal to the to date.")
public class TraineeTrainingsRequestDTO {

    @NotNull(message = "username is required")
    @NotBlank(message = "username is required")
    private String username;

    @JsonDeserialize(using = DateDeSerializer.class)
    private Date fromDate;

    @JsonDeserialize(using = DateDeSerializer.class)
    private Date toDate;

    private String trainerName;

    @ValidTrainingType
    private String trainingTypeName;

}
