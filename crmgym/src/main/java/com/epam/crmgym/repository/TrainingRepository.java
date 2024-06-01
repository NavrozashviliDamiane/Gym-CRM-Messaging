package com.epam.crmgym.repository;

import com.epam.crmgym.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByTraineeId(Long traineeId);

    List<Training> findByTrainerId(Long trainerId);


    List<Training> findByTraineeIdAndTrainingDateBetweenAndTrainerIdAndTrainingTypeId(
            Long traineeId, Date fromDate, Date toDate, Long trainerId, Long trainingTypeId);

    List<Training> findByTrainerIdAndTrainingDateBetweenAndTraineeId(
            Long traineeId, Date fromDate, Date toDate, Long trainerId);


    List<Training> findByTraineeIdAndTrainerIdAndTrainingTypeId(Long traineeId, Long trainerId, Long trainingTypeId);

    List<Training> findByTraineeIdAndTrainerId(Long traineeId, Long trainerId);

    List<Training> findByTraineeIdAndTrainingTypeId(Long traineeId, Long trainingTypeId);

    List<Training> findByTrainerIdAndTraineeId(Long trainerId, Long traineeId);

    List<Training> findByTrainerIdAndTrainingDateBetween(Long trainerId, Date fromDate, Date toDate);

    List<Training> findByTrainerIdAndTrainingDateAfter(Long trainerId, Date fromDate);

    List<Training> findByTrainerIdAndTrainingDateBefore(Long trainerId, Date toDate);




    @Query("SELECT t FROM Training t WHERE t.trainee.id = :tnId AND t.trainingDate BETWEEN :frDt AND :toDt AND t.trainer.id = :trId AND t.trainingType.id = :tyId AND t.trainingDuration > :dur")
    List<Training> findTrByTnIdAndDtBtwnAndTrIdAndTyIdAndDurGt(@Param("tnId") Long tnId, @Param("frDt") Date frDt, @Param("toDt") Date toDt, @Param("trId") Long trId, @Param("tyId") Long tyId, @Param("dur") int dur);

    @Query("SELECT t FROM Training t WHERE t.trainee.id = :tnId AND t.trainingDate BETWEEN :frDt AND :toDt AND t.trainer.id = :trId AND t.trainingDuration > :dur")
    List<Training> findTrByTnIdAndDtBtwnAndTrIdAndDurGt(@Param("tnId") Long tnId, @Param("frDt") Date frDt, @Param("toDt") Date toDt, @Param("trId") Long trId, @Param("dur") int dur);

    @Query("SELECT t FROM Training t WHERE t.trainee.id = :tnId AND t.trainingDate BETWEEN :frDt AND :toDt AND t.trainingType.id = :tyId AND t.trainingDuration > :dur")
    List<Training> findTrByTnIdAndDtBtwnAndTyIdAndDurGt(@Param("tnId") Long tnId, @Param("frDt") Date frDt, @Param("toDt") Date toDt, @Param("tyId") Long tyId, @Param("dur") int dur);

    @Query("SELECT t FROM Training t WHERE t.trainee.id = :tnId AND t.trainingDate BETWEEN :frDt AND :toDt AND t.trainingDuration > :dur")
    List<Training> findTrByTnIdAndDtBtwnAndDurGt(@Param("tnId") Long tnId, @Param("frDt") Date frDt, @Param("toDt") Date toDt, @Param("dur") int dur);



    @Query("SELECT t FROM Training t WHERE t.trainee.id = :tnId AND t.trainingDate >= :frDt AND t.trainer.id = :trId AND t.trainingType.id = :tyId AND t.trainingDuration > :dur")
    List<Training> findTrByTnIdAndDtGtEqAndTrIdAndTyIdAndDurGt(@Param("tnId") Long tnId, @Param("frDt") Date frDt, @Param("trId") Long trId, @Param("tyId") Long tyId, @Param("dur") int dur);

    @Query("SELECT t FROM Training t WHERE t.trainee.id = :tnId AND t.trainingDate >= :frDt AND t.trainer.id = :trId AND t.trainingDuration > :dur")
    List<Training> findTrByTnIdAndDtGtEqAndTrIdAndDurGt(@Param("tnId") Long tnId, @Param("frDt") Date frDt, @Param("trId") Long trId, @Param("dur") int dur);

    @Query("SELECT t FROM Training t WHERE t.trainee.id = :tnId AND t.trainingDate >= :frDt AND t.trainingType.id = :tyId AND t.trainingDuration > :dur")
    List<Training> findTrByTnIdAndDtGtEqAndTyIdAndDurGt(@Param("tnId") Long tnId, @Param("frDt") Date frDt, @Param("tyId") Long tyId, @Param("dur") int dur);

    @Query("SELECT t FROM Training t WHERE t.trainee.id = :tnId AND t.trainingDate >= :frDt AND t.trainingDuration > :dur")
    List<Training> findTrByTnIdAndDtGtEqAndDurGt(@Param("tnId") Long tnId, @Param("frDt") Date frDt, @Param("dur") int dur);
}
