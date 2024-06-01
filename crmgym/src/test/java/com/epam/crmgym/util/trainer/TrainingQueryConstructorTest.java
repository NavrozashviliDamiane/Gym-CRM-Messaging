package com.epam.crmgym.util.trainer;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.epam.crmgym.entity.Training;
import com.epam.crmgym.repository.TrainingRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TrainingQueryConstructorTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingQueryConstructor queryConstructor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConstructQuery_WithAllParameters() {
        Long trainerId = 1L;
        Date fromDate = new Date();
        Date toDate = new Date();
        Long traineeId = 2L;
        List<Training> expectedTrainings = new ArrayList<>();
        when(trainingRepository.findByTrainerIdAndTrainingDateBetweenAndTraineeId(trainerId, fromDate, toDate, traineeId))
                .thenReturn(expectedTrainings);

        List<Training> queryResult = queryConstructor.constructQuery(trainerId, fromDate, toDate, traineeId);

        assertEquals(expectedTrainings, queryResult);
    }

}
