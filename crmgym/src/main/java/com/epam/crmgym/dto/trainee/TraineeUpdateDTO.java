package com.epam.crmgym.dto.trainee;

import com.epam.crmgym.exception.DateDeSerializer;
import com.epam.crmgym.validation.RequiredBoolean;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
public class TraineeUpdateDTO {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;


    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Past(message = "Date of birth must be in the past")
    @JsonDeserialize(using = DateDeSerializer.class)
    private Date dateOfBirth;

    private String address;

    @NotNull(message = "Status field is required")
    @RequiredBoolean(message = "Status field is required")
    private Boolean isActive;

}

