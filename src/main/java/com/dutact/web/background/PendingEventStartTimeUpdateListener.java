package com.dutact.web.background;

import com.dutact.web.common.notification.core.timer.ScheduledJob;
import com.dutact.web.common.notification.core.timer.ScheduledJobRepository;
import com.dutact.web.common.notification.core.timer.ScheduledJobType;
import com.dutact.web.event.event.PendingEventStartTimeUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PendingEventStartTimeUpdateListener {
    private final ScheduledJobRepository scheduledJobRepository;

    @Async
    @EventListener
    @Transactional
    public void handle(PendingEventStartTimeUpdatedEvent event) {
        deleteScheduledJob(event.eventId());
        createScheduledJob(event.eventId(), event.newStartAt());
    }

    private void deleteScheduledJob(int eventId) {
        scheduledJobRepository.deleteAllByCompareStringAndType(String.valueOf(eventId), ScheduledJobType.AUTO_REJECT_PENDING_EVENT);
    }

    private void createScheduledJob(int eventId, LocalDateTime startAt) {
        var scheduledJob = new ScheduledJob();
        scheduledJob.setCompareString(String.valueOf(eventId));
        scheduledJob.setDetails(String.valueOf(eventId));
        scheduledJob.setType(ScheduledJobType.AUTO_REJECT_PENDING_EVENT);
        scheduledJob.setFireAt(startAt);
        scheduledJob.setExpireAt(startAt.plusDays(1));
        scheduledJobRepository.save(scheduledJob);
    }
}
