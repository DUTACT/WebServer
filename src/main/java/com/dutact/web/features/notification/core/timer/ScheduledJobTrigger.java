package com.dutact.web.features.notification.core.timer;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@AllArgsConstructor
public class ScheduledJobTrigger {
    private final ScheduledJobRepository scheduledJobRepository;
    private final ScheduledJobDelegator scheduledJobDelegator;

    @Scheduled(fixedRate = 1000 * 60)
    public void schedule() {
        var now = System.currentTimeMillis();
        var nowLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(now), ZoneId.systemDefault());
        var scheduledJobs = scheduledJobRepository.getAllByFireAtBefore(nowLocalDateTime);
        scheduledJobs.forEach(scheduledJobDelegator::executeScheduledJob);

    }
}
