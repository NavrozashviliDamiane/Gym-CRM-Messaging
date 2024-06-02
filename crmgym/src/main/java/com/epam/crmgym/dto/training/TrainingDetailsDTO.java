package com.epam.crmgym.dto.training;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDetailsDTO {
    private Date trainingDate;
    private int trainingDuration;

    // Constructors, getters, and setters
}

