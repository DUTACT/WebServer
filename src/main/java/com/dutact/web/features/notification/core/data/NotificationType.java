package com.dutact.web.features.notification.core.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationType {
    public static final String POST_CREATED = "post_created";
    public static final String EVENT_START_REMIND = "event_start_remind";
}
