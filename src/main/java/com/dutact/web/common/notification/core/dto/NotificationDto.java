package com.dutact.web.common.notification.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("details")
    private Object details;

    @JsonProperty("notification_type")
    private String notificationType;

    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("is_read")
    private boolean isRead;
}
