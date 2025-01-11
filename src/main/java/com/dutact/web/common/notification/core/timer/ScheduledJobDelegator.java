package com.dutact.web.common.notification.core.timer;

public interface ScheduledJobDelegator {
    void executeScheduledJob(DelegateScheduledJob scheduledJob);
}
