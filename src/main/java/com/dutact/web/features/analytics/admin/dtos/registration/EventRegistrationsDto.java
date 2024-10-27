package com.dutact.web.features.analytics.admin.dtos.registration;

import lombok.Data;

import java.util.List;

@Data
public class EventRegistrationsDto {
    private List<EventRegistrationByDateDto> registrations;
}
