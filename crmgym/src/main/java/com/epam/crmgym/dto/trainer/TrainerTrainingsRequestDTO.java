package com.epam.crmgym.dto.trainer;

import com.epam.crmgym.exception.DateDeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainingsRequestDTO {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;

    @JsonDeserialize(using = DateDeSerializer.class)
    private Date periodFrom;

    @JsonDeserialize(using = DateDeSerializer.class)
    private Date periodTo;

    private String traineeName;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String password;
}
