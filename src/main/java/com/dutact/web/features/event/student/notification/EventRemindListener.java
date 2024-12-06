package com.dutact.web.features.event.student.notification;

import com.dutact.web.features.event.events.PublishedEventStartTimeUpdated;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventRemindListener {
    @Async
    @EventListener
    public void onEventStartTimeUpdated(PublishedEventStartTimeUpdated event) {

    }
}
