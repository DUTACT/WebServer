package com.dutact.web.features.notification.infrastructure.api.dtos;

import lombok.Data;

@Data
public class UnsubscribeDto {
    private String subscriptionToken;
}
