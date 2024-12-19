package com.dutact.web.features.notification.core.timer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduledJobType {
    public static final String EVENT_REMINDER = "EVENT_REMINDER";
    public static final String AUTO_REJECT_PENDING_EVENT = "AUTO_REJECT_PENDING_EVENT";
}
