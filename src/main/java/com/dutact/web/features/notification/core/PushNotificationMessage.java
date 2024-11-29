package com.dutact.web.features.notification.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PushNotificationMessage {
    @JsonProperty("notificationId")
    private Object notificationId;

    @JsonProperty("details")
    private Object details;

    @JsonProperty("notificationType")
    private String notificationType;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}
