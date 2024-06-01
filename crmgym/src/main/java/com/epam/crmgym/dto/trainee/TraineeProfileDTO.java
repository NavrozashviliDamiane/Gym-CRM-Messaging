package com.epam.crmgym.dto.trainee;

import com.epam.crmgym.dto.trainer.TrainerDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeProfileDTO {
    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date dateOfBirth;

    private String address;
    private boolean active;
    private List<TrainerDTO> trainers;

}
