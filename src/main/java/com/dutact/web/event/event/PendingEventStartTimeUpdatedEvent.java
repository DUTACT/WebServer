package com.dutact.web.event.event;

import java.time.LocalDateTime;

public record PendingEventStartTimeUpdatedEvent(int eventId,
                                                LocalDateTime newStartAt) {
}
