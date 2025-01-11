package com.dutact.web.common.notification.core.timer;

public interface ScheduledJobExecutor {
    void handleScheduledJob(DelegateScheduledJob scheduledJob);
}
