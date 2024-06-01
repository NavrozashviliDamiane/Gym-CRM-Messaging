//package com.epam.crmgym.metrics;
//
//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.Timer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomMetrics {
//
//    private final MeterRegistry meterRegistry;
//    private final Counter traineeProfileCounter;
//    private final Timer updateTraineeProfileTimer;
//
//    @Autowired
//    public CustomMetrics(MeterRegistry meterRegistry) {
//        this.meterRegistry = meterRegistry;
//        this.traineeProfileCounter = Counter.builder("trainee_profile_requests_total")
//                .description("Total number of trainee profile requests")
//                .register(meterRegistry);
//        this.updateTraineeProfileTimer = Timer.builder("update_trainee_profile_duration_seconds")
//                .description("Duration of update trainee profile method")
//                .register(meterRegistry);
//    }
//
//
//    public Counter getTraineeProfileCounter() {
//        return traineeProfileCounter;
//    }
//
//    public Timer getUpdateTraineeProfileTimer() {
//        return updateTraineeProfileTimer;
//    }
//}
