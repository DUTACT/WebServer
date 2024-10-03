package com.dutact.web.features.event.admin.services;

import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.features.event.admin.dtos.EventCreateUpdateDto;
import com.dutact.web.features.event.admin.dtos.EventDto;

import java.util.Collection;
import java.util.Optional;

public interface EventService {
    EventDto createEvent(Integer orgId, EventCreateUpdateDto eventDto);

    Collection<EventDto> getEvents();

    Collection<EventDto> getEvents(Integer orgId);

    Optional<EventDto> getEvent(Integer eventId);

    EventDto updateEvent(Integer eventId, EventCreateUpdateDto eventDto);

    EventStatus updateEventStatus(Integer eventId, EventStatus eventStatus);

    void deleteEvent(Integer eventId);

    boolean eventExists(Integer orgId, Integer eventId);
}
