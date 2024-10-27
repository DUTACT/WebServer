package com.dutact.web.features.analytics.admin.services;


import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationsDto;

public interface EventAnalyticsService {
    EventRegistrationsDto getEventRegistrations(Integer eventId) throws NotExistsException;
}
