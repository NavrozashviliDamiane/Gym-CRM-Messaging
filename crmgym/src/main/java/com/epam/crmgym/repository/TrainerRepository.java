package com.epam.crmgym.repository;

import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @EntityGraph(attributePaths = "user")
    Trainer findByUserUsername(String username);

    Trainer findByUser(User user);

    List<Trainer> findByUserUsernameIn(List<String> trainerUsernames);
}
