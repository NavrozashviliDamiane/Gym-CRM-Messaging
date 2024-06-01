package com.epam.trainerworkload.repository;

import com.epam.trainerworkload.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    @Query("SELECT t FROM TrainingSession t WHERE t.trainerUsername = :username AND FUNCTION('YEAR', t.trainingDate) = :year AND FUNCTION('MONTH', t.trainingDate) = :month")
    List<TrainingSession> findTrainingSessionsByTrainerAndMonth(String username, int year, int month);

    List<TrainingSession> findByTrainerUsernameAndTrainingDate(String trainerUsername, Date trainingDate);

}
