package com.dutact.web.features.event.events;

import java.time.LocalDateTime;

public record PublishedEventStartTimeUpdated(int eventId, LocalDateTime startAt) {
}
