package com.epam.trainerworkload.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainerUsername;
    private String firstName;
    private String lastName;
    private Boolean isActive;

    @Temporal(TemporalType.DATE)
    private Date trainingDate;

    private Integer trainingDuration;

}

