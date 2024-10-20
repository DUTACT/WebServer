package com.dutact.web.features.event.admin.services.event;

import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.features.event.admin.dtos.event.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.event.EventUpdateDto;

import java.util.Collection;
import java.util.Optional;

public interface EventService {
    EventDto createEvent(EventCreateDto eventDto);

    Collection<EventDto> getEvents();

    Collection<EventDto> getEvents(Integer orgId);

    Optional<EventDto> getEvent(Integer eventId);

    EventDto updateEvent(Integer eventId, EventUpdateDto eventDto);
    
    EventStatus approveEvent(Integer eventId);

    EventStatus rejectEvent(Integer eventId, String reason);

    void deleteEvent(Integer eventId);

    boolean eventExists(Integer orgId, Integer eventId);
}
