package com.epam.crmgym.dto.client;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSessionDTO {
    private String trainerUsername;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private Date trainingDate;
    private Integer trainingDuration;
    private String actionType;

}

