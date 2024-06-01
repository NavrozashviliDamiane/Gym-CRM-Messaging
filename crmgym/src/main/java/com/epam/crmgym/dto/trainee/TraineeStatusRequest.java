package com.epam.crmgym.dto.trainee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeStatusRequest {

    private String username;
    private boolean isActive;

}

