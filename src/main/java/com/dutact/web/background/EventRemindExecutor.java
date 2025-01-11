package com.dutact.web.background;

import com.dutact.web.common.mapper.ObjectMapperUtils;
import com.dutact.web.common.notification.core.NotificationData;
import com.dutact.web.common.notification.core.NotificationDeliveryCentral;
import com.dutact.web.common.notification.core.data.NotificationType;
import com.dutact.web.common.notification.core.timer.DelegateScheduledJob;
import com.dutact.web.common.notification.core.timer.ScheduledJobExecutor;
import com.dutact.web.data.repository.EventRegistrationRepository;
import com.dutact.web.data.repository.EventRepository;
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
    private final EventRegistrationRepository eventRegistrationRepository;
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

        var studentIds = eventRegistrationRepository.findStudentIdsByEventId(event.getId());

        var notificationData = new NotificationData();
        notificationData.setAccountIds(studentIds);
        notificationData.setDetails(notification);
        notificationData.setNotificationType(NotificationType.EVENT_START_REMIND);
        notificationData.setExpireAt(details.getExpireAt());
        notificationDeliveryCentral.sendNotification(notificationData);
    }
}
