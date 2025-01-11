package com.dutact.web.common.notification.infrastructure.api.dtos;

import lombok.Data;

@Data
public class UnsubscribeDto {
    private String subscriptionToken;
}
