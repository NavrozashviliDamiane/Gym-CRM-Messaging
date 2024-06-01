package com.epam.crmgym.dto.trainer;

import com.epam.crmgym.validation.RequiredBoolean;
import com.epam.crmgym.validation.ValidTrainingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerUpdateDTO {


    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;


    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Status field is required")
    @RequiredBoolean(message = "Status field is required")
    private Boolean isActive;

    @NotNull(message = "Specialization name is required")
    @NotBlank(message = "Specialization name is required")
    @ValidTrainingType
    private String specialization;
}

