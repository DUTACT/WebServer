package com.dutact.web.features.event.student.notification.reminder;

import com.dutact.web.common.mapper.ObjectMapperUtils;
import com.dutact.web.features.event.events.PublishedEventStartTimeUpdatedEvent;
import com.dutact.web.features.notification.core.timer.ScheduledJob;
import com.dutact.web.features.notification.core.timer.ScheduledJobRepository;
import com.dutact.web.features.notification.core.timer.ScheduledJobType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Log4j2
@Component
public class EventRemindListener {
    private final ObjectMapper objectMapper = ObjectMapperUtils.createObjectMapper();
    private final ScheduledJobRepository scheduledJobRepository;
    private int[] remindMins;

    public EventRemindListener(@Value("${notification.remind.event.mins}") String remindMinsStr,
                               ScheduledJobRepository scheduledJobRepository) {
        this.scheduledJobRepository = scheduledJobRepository;
        var remindMins = remindMinsStr.split(",");
        this.remindMins = new int[remindMins.length];

        for (int i = 0; i < remindMins.length; i++) {
            this.remindMins[i] = Integer.parseInt(remindMins[i]);
        }
    }

    @Async
    @EventListener
    @Transactional
    public void onEventStartTimeUpdated(PublishedEventStartTimeUpdatedEvent event) {
        if (event.oldStartAt() != null) {
            deleteRemindJob(event.eventId(), event.oldStartAt());
        }

        createRemindJobs(event.eventId(), event.newStartAt());
    }

    private void deleteRemindJob(Integer eventId, LocalDateTime startAt) {
        var compareStr = calculateCompareStr(eventId, startAt);

        scheduledJobRepository.deleteAllByCompareStringAndType(compareStr, ScheduledJobType.EVENT_REMINDER);
    }

    private void createRemindJobs(Integer eventId, LocalDateTime startAt) {
        var scheduledJobs = new ArrayList<ScheduledJob>();
        var compareStr = calculateCompareStr(eventId, startAt);

        for (var remindMin : remindMins) {
            try {
                var remindAt = startAt.minusMinutes(remindMin);
                var data = new EventRemindDetails();
                data.setEventId(eventId);
                data.setStartAt(startAt);

                var scheduledJob = new ScheduledJob();
                scheduledJob.setType(ScheduledJobType.EVENT_REMINDER);
                scheduledJob.setFireAt(remindAt);
                scheduledJob.setDetails(objectMapper.writeValueAsString(data));
                scheduledJob.setCompareString(compareStr);

                scheduledJobs.add(scheduledJob);
            } catch (Exception e) {
                log.error("Failed to create remind job for event {} at {}", eventId, remindMin, e);
            }
        }

        scheduledJobRepository.saveAll(scheduledJobs);
    }

    private String calculateCompareStr(Integer eventId, LocalDateTime startAt) {
        return eventId + ":" + startAt.toString();
    }

}


