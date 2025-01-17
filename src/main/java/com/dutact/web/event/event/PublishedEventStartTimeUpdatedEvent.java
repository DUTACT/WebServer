package com.dutact.web.event.event;

import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

public record PublishedEventStartTimeUpdatedEvent(int eventId,
                                                  @Nullable LocalDateTime oldStartAt,
                                                  LocalDateTime newStartAt) {
}
