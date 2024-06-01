package com.epam.crmgym.dto.user;

import com.epam.crmgym.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "Old Password is required")
    @NotBlank(message = "Old Password is required")
    private String oldPassword;

    @NotNull(message = "New Password is required")
    @NotBlank(message = "New Password is required")
    @ValidPassword
    private String newPassword;

}

