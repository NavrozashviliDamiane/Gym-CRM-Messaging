package com.epam.crmgym.dto.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedTrainersListResponseDTO {
    private List<TrainerDTO> trainersList;
}
