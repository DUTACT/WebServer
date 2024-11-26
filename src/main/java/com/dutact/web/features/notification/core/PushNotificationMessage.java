package com.dutact.web.features.notification.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PushNotificationMessage {
    @JsonProperty("id")
    private Object id;

    @JsonProperty
    private Integer accountId;

    @JsonProperty("details")
    private Object details;

    @JsonProperty("notificationType")
    private String notificationType;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}
