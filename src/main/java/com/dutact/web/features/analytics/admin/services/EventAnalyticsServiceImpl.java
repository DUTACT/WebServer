package com.dutact.web.features.analytics.admin.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.repositories.EventRegistrationRepository;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationQueryParams;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationsDto;
import org.springframework.stereotype.Service;

@Service
public class EventAnalyticsServiceImpl implements EventAnalyticsService {
    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;

    public EventAnalyticsServiceImpl(EventRepository eventRepository,
                                     EventRegistrationRepository eventRegistrationRepository) {
        this.eventRepository = eventRepository;
        this.eventRegistrationRepository = eventRegistrationRepository;
    }

    @Override
    public EventRegistrationsDto getEventRegistrations(EventRegistrationQueryParams queryParams) throws NotExistsException {
        return null;
    }

    @Override
    public EventRegistrationsDto getEventRegistrations(Integer eventId) throws NotExistsException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotExistsException("Event not found"));
    }
}
