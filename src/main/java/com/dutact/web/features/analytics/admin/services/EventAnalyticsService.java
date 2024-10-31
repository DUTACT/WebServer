package com.dutact.web.features.analytics.admin.services;


import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationCountByDateDto;

import java.util.List;

public interface EventAnalyticsService {
    List<EventRegistrationCountByDateDto> getEventRegistrations(
            Integer eventId)
            throws NotExistsException;
}