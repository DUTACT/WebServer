package com.dutact.web.features.notification.core.timer;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ScheduledJobDelegatorImpl implements ScheduledJobDelegator {
    @Override
    public void executeScheduledJob(ScheduledJob scheduledJob) {
        try {

        } catch (Exception e) {
            log.error("Failed to execute scheduled job", e);
        }
    }
}
