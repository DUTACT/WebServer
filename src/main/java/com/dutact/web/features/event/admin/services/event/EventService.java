package com.dutact.web.features.event.admin.services.event;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.features.event.admin.dtos.event.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.event.EventUpdateDto;

import java.util.List;
import java.util.Optional;

public interface EventService {
    EventDto createEvent(Integer organizerId, EventCreateDto eventDto) throws NotExistsException;

    List<EventDto> getEvents();

    List<EventDto> getEvents(Integer orgId) throws NotExistsException;

    Optional<EventDto> getEvent(Integer eventId);

    EventDto updateEvent(Integer eventId, EventUpdateDto eventDto) throws NotExistsException;

    EventStatus approveEvent(Integer eventId) throws NotExistsException;

    EventStatus rejectEvent(Integer eventId, String reason) throws NotExistsException;

    void deleteEvent(Integer eventId);

    boolean eventExists(Integer orgId, Integer eventId);
}
