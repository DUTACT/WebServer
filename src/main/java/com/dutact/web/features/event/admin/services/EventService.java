package com.dutact.web.features.event.admin.services;

import com.dutact.web.features.event.admin.dtos.EventCreateUpdateDto;
import com.dutact.web.features.event.admin.dtos.EventDto;

import java.util.Collection;

public interface EventService {
    EventDto createEvent(Integer orgId, EventCreateUpdateDto eventDto);

    Collection<EventDto> getEvents(Integer orgId);

    EventDto getEvent(Integer eventId);

    EventDto updateEvent(Integer eventId, EventCreateUpdateDto eventDto);

    void deleteEvent(Integer eventId);

    boolean eventExists(Integer orgId, Integer eventId);
}
