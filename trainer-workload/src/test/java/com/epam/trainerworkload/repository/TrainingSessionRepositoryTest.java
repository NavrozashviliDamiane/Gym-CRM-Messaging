package com.epam.trainerworkload.repository;

import com.epam.trainerworkload.entity.TrainingSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TrainingSessionRepositoryTest {

    @Autowired
    private TrainingSessionRepository repository;

    @Test
    void saveAndFindTrainingSession() {
        TrainingSession session = new TrainingSession(
                null,
                "john_doe",
                "John",
                "Doe",
                true,
                new Date(),
                60
        );
        TrainingSession savedSession = repository.save(session);

        assertNotNull(savedSession.getId());
        assertEquals("john_doe", savedSession.getTrainerUsername());
        assertEquals("John", savedSession.getFirstName());
        assertEquals("Doe", savedSession.getLastName());
        assertTrue(savedSession.getIsActive());
        assertEquals(60, savedSession.getTrainingDuration());

        List<TrainingSession> sessions = repository.findByTrainerUsernameAndTrainingDate(
                "john_doe",
                session.getTrainingDate()
        );
        assertEquals(1, sessions.size());
        assertEquals(savedSession.getId(), sessions.get(0).getId());
    }

    @Test
    void findTrainingSessionsByTrainerAndMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, 5); // June (0-based index)
        calendar.set(Calendar.DAY_OF_MONTH, 15);

        TrainingSession session1 = new TrainingSession(
                null,
                "john_doe",
                "John",
                "Doe",
                true,
                calendar.getTime(),
                60
        );

        calendar.set(Calendar.DAY_OF_MONTH, 20);
        TrainingSession session2 = new TrainingSession(
                null,
                "john_doe",
                "John",
                "Doe",
                true,
                calendar.getTime(),
                90
        );

        repository.save(session1);
        repository.save(session2);

        List<TrainingSession> sessions = repository.findTrainingSessionsByTrainerAndMonth("john_doe", 2024, 6);
        assertEquals(2, sessions.size());
        assertTrue(sessions.stream().anyMatch(s -> s.getTrainingDuration() == 60));
        assertTrue(sessions.stream().anyMatch(s -> s.getTrainingDuration() == 90));
    }
}
