package com.epam.trainerworkload.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTotalTrainingHoursRequestDTO {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "Year is required")
    @Min(value = 2000, message = "Year must be at least 2000")
    private Integer year;

    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be at least 1")
    @Max(value = 12, message = "Month must be at most 12")
    private Integer month;
}
