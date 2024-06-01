package com.epam.crmgym.dto.trainee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TraineeUpdateRequest {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "password is required")
    @NotBlank(message = "Password is required")
    private String password;

    private TraineeUpdateDTO updateDTO;
}

