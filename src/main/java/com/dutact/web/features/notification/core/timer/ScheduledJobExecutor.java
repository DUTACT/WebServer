package com.dutact.web.features.notification.core.timer;

public interface ScheduledJobExecutor {
    void handleScheduledJob(DelegateScheduledJob scheduledJob);
}
