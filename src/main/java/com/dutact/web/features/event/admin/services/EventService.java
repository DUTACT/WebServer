package com.dutact.web.features.event.admin.services;

import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.features.event.admin.dtos.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.EventDto;
import com.dutact.web.features.event.admin.dtos.EventUpdateDto;

import java.util.Collection;
import java.util.Optional;

public interface EventService {
    EventDto createEvent(EventCreateDto eventDto);

    Collection<EventDto> getEvents();

    Collection<EventDto> getEvents(Integer orgId);

    Optional<EventDto> getEvent(Integer eventId);

    EventDto updateEvent(Integer eventId, EventUpdateDto eventDto);

    EventStatus updateEventStatus(Integer eventId, EventStatus eventStatus);

    void deleteEvent(Integer eventId);

    boolean eventExists(Integer orgId, Integer eventId);
}
