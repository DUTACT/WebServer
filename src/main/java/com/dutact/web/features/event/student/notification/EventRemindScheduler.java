package com.dutact.web.features.event.student.notification;

import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.features.notification.core.NotificationDeliveryCentral;
import com.dutact.web.features.notification.core.timer.ScheduledJobRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventRemindScheduler {
    private ScheduledJobRepository timerQueueRepository;
    private EventRepository eventRepository;
    private NotificationDeliveryCentral notificationDeliveryCentral;


    @Scheduled(fixedRate = 1000 * 60)
    public void remindEvent() {

    }
}
