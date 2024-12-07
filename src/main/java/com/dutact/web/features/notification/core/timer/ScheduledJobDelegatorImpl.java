package com.dutact.web.features.notification.core.timer;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
@AllArgsConstructor
public class ScheduledJobDelegatorImpl implements ScheduledJobDelegator {
    private final Map<String, ScheduledJobExecutor> scheduledJobExecutorMap;

    @Override
    public void executeScheduledJob(DelegateScheduledJob scheduledJob) {
        try {
            var scheduledJobExecutor = scheduledJobExecutorMap.get(scheduledJob.getType());
            if (scheduledJobExecutor == null) {
                log.error("No executor found for scheduled job type: {}", scheduledJob.getType());
                return;
            }
            scheduledJobExecutor.handleScheduledJob(scheduledJob);
        } catch (Exception e) {
            log.error("Failed to execute scheduled job", e);
        }
    }
}
