package com.dutact.web.features.event.admin.background;

import com.dutact.web.core.entities.event.CannotChangeStatusException;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.features.notification.core.timer.DelegateScheduledJob;
import com.dutact.web.features.notification.core.timer.ScheduledJobExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class AutoRejectEventExecutor implements ScheduledJobExecutor {
    public final EventRepository eventRepository;

    @Override
    public void handleScheduledJob(DelegateScheduledJob scheduledJob) {
        var eventId = Integer.parseInt(scheduledJob.getDetails());
        var eventOtp = eventRepository.findById(eventId);

        if (eventOtp.isEmpty()) {
            log.info("Event with id {} not found", eventId);
            return;
        }

        var event = eventOtp.get();
        try {
            var status = new EventStatus.Rejected("Sự kiện tự động bị từ chối do không được duyệt");
            event.setStatus(status);
            log.info("Auto reject event with id {}", eventId);
        } catch (CannotChangeStatusException e) {
            log.info("Cannot auto reject event with id {}", eventId);
        }

        eventRepository.save(event);
    }
}