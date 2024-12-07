package com.dutact.web.features.event.student.notification;

import com.dutact.web.common.mapper.ObjectMapperUtils;
import com.dutact.web.core.repositories.EventFollowRepository;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.features.notification.core.NotificationDeliveryCentral;
import com.dutact.web.features.notification.core.NotificationType;
import com.dutact.web.features.notification.core.timer.DelegateScheduledJob;
import com.dutact.web.features.notification.core.timer.ScheduledJobExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class EventRemindExecutor implements ScheduledJobExecutor {
    private final ObjectMapper objectMapper = ObjectMapperUtils.createObjectMapper();
    private final EventRepository eventRepository;
    private final EventFollowRepository eventFollowRepository;
    private final NotificationDeliveryCentral notificationDeliveryCentral;

    @SneakyThrows
    @Override
    public void handleScheduledJob(DelegateScheduledJob scheduledJob) {
        var details = objectMapper.readValue(scheduledJob.getDetails(), EventRemindDetails.class);

        if (details.getType().equals(EventRemindType.EVENT_START)) {
            remindEventStart(details);
        } else {
            log.error("Unknown event remind type: {}", details.getType());
        }
    }

    @SneakyThrows
    private void remindEventStart(EventRemindDetails details) {
        var event = eventRepository.findById(details.getEventId()).orElseThrow();

        var notification = new EventRemindNotification.EventStart();
        notification.setEventId(event.getId());
        notification.setEventName(event.getName());
        notification.setStartAt(event.getStartAt());

        var studentIds = eventFollowRepository.findStudentIdsByEventId(event.getId());

        notificationDeliveryCentral.sendNotification(studentIds, notification, NotificationType.EVENT_START_REMIND);
    }
}