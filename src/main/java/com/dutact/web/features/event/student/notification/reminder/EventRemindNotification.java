package com.dutact.web.features.event.student.notification.reminder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public abstract class EventRemindNotification {
    private Integer eventId;

    private String eventName;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class EventStart extends EventRemindNotification {
        private LocalDateTime startAt;
    }
}
