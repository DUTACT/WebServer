package com.dutact.web.configuration;

import com.dutact.web.features.event.student.notification.reminder.EventRemindExecutor;
import com.dutact.web.features.notification.core.timer.ScheduledJobDelegator;
import com.dutact.web.features.notification.core.timer.ScheduledJobDelegatorImpl;
import com.dutact.web.features.notification.core.timer.ScheduledJobExecutor;
import com.dutact.web.features.notification.core.timer.ScheduledJobType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@AllArgsConstructor
public class ScheduledJobConfiguration {
    private final EventRemindExecutor eventRemindExecutor;

    @Bean
    public ScheduledJobDelegator scheduledJobDelegator() {
        var map = Map.of(ScheduledJobType.EVENT_REMINDER, (ScheduledJobExecutor) eventRemindExecutor);
        return new ScheduledJobDelegatorImpl(map);
    }
}
