package com.epam.crmgym.client;

import com.epam.crmgym.config.FeignConfig;
import com.epam.crmgym.dto.client.TrainingSessionDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "trainer-workload-service", configuration = FeignConfig.class)
public interface TrainerWorkloadClient {

    Logger logger = LoggerFactory.getLogger(TrainerWorkloadClient.class);

    @PostMapping("/workload")
    @CircuitBreaker(name = "trainerWorkloadService", fallbackMethod = "fallback")
    String manageTrainingSession(@RequestBody TrainingSessionDTO session);


    default String fallback(TrainingSessionDTO session, Throwable throwable) {
        logger.error("Fallback method triggered. Reason: {}", throwable.getMessage(), throwable);
        return "FALLBACK: Service is currently unavailable. Please try again later.";
    }
}
