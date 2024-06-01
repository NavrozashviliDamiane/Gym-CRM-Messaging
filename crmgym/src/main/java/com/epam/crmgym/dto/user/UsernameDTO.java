package com.epam.crmgym.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameDTO {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;

}

