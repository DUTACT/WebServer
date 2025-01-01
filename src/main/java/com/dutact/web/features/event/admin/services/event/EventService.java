package com.dutact.web.features.event.admin.services.event;

import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.features.event.admin.dtos.event.*;

import java.util.List;
import java.util.Optional;

public interface EventService {
    EventDto createEvent(Integer organizerId, EventCreateDtoV2 eventDto) throws NotExistsException, ConflictException;

    EventDto createEvent(Integer organizerId, EventCreateDtoV1 eventDto) throws NotExistsException, ConflictException;

    List<EventDto> getEvents();

    List<EventDto> getEvents(Integer orgId) throws NotExistsException;

    Optional<EventDto> getEvent(Integer eventId);

    List<EventChangeDto> getEventChangeHistory(Integer eventId);

    EventDto updateEvent(Integer eventId, EventUpdateDtoV2 eventDto) throws NotExistsException;

    EventDto updateEvent(Integer eventId, EventUpdateDtoV1 eventDto) throws NotExistsException;

    EventDto renewEventRegistration(Integer eventId, RenewEventRegistrationDto renewEventRegistrationDto)
            throws NotExistsException;

    EventDto changeEventTime(Integer eventId, ChangeEventTimeDto changeEventTimeDto)
            throws NotExistsException;

    EventDto closeEventRegistration(Integer eventId) throws NotExistsException, ConflictException;

    EventStatus approveEvent(Integer eventId) throws NotExistsException, ConflictException;

    EventStatus rejectEvent(Integer eventId, String reason) throws NotExistsException, ConflictException;

    void deleteEvent(Integer eventId);

    boolean eventExists(Integer orgId, Integer eventId);
}
