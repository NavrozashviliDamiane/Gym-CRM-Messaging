package com.epam.crmgym.dto.user;

import com.epam.crmgym.validation.RequiredBoolean;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserStatusRequestDTO {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;


    @NotNull(message = "Status field is required")
    @RequiredBoolean(message = "Status field is required")
    private Boolean isActive;
}
