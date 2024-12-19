package com.dutact.web.features.event.events;

import java.time.LocalDateTime;

public record PendingEventStartTimeUpdatedEvent(int eventId,
                                                LocalDateTime newStartAt) {
}
