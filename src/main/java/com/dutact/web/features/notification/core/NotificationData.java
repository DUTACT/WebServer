package com.dutact.web.features.notification.core;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class NotificationData {
    private Collection<Integer> accountIds;
    
    private Object details;

    private String notificationType;

    @Nullable
    private LocalDateTime expireAt;
}
