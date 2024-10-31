package com.dutact.web.features.analytics.admin.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.projections.RegistrationCountByDate;
import com.dutact.web.core.repositories.EventRegistrationRepository;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationCountByDateDto;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<EventRegistrationCountByDateDto> getEventRegistrations(
            Integer eventId) throws NotExistsException {
        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException("Event not found.");
        }

        List<RegistrationCountByDate> registrations = eventRegistrationRepository.countRegistrationByDate(eventId);

        return registrations.stream().map(this::toDto).toList();
    }

    private EventRegistrationCountByDateDto toDto(RegistrationCountByDate registration) {
        var dto = new EventRegistrationCountByDateDto();
        dto.setDate(registration.getDate().toLocalDate());
        dto.setCount(registration.getCount().intValue());
        return dto;
    }
}
