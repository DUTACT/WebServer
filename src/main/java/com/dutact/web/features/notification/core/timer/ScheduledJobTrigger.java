package com.dutact.web.features.notification.core.timer;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
@AllArgsConstructor
public class ScheduledJobTrigger {
    private final ScheduledJobRepository scheduledJobRepository;
    private final ScheduledJobDelegator scheduledJobDelegator;

    @Transactional
    @Scheduled(fixedRateString = "${notification.scheduled-job.interval-secs}", timeUnit = TimeUnit.SECONDS)
    public void schedule() {
        var now = LocalDateTime.now();
        var scheduledJobs = scheduledJobRepository
                .getAllByFireAtBeforeAndExpireAtAfter(now, now);
        var delegateScheduledJobs = scheduledJobs.stream().map((scheduledJob) ->
        {
            var delegateScheduledJob = new DelegateScheduledJob();
            delegateScheduledJob.setType(scheduledJob.getType());
            delegateScheduledJob.setDetails(scheduledJob.getDetails());
            delegateScheduledJob.setCompareString(scheduledJob.getCompareString());

            return delegateScheduledJob;
        });
        scheduledJobRepository.deleteAll(scheduledJobs);

        delegateScheduledJobs.forEach(scheduledJobDelegator::executeScheduledJob);
        log.trace("Scheduled job trigger executed, {} jobs executed", scheduledJobs.size());
    }

    @Transactional
    @Scheduled(cron = "${notification.scheduled-job.cleanup-cron}")
    public void cleanupExpiredJobs() {
        var now = LocalDateTime.now();
        scheduledJobRepository.deleteAllByExpireAtBefore(now);
        log.trace("Deleted expired scheduled jobs");
    }
}
