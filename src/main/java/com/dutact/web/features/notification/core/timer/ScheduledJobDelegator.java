package com.dutact.web.features.notification.core.timer;

public interface ScheduledJobDelegator {
    void executeScheduledJob(DelegateScheduledJob scheduledJob);
}
