package com.dutact.web.features.event.student.services;

import com.dutact.web.features.event.student.dtos.EventDto;
import com.dutact.web.features.event.student.dtos.EventRegisteredDto;
import com.dutact.web.features.event.student.services.exceptions.AlreadyRegisteredException;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Optional<EventDto> getEvent(Integer id);

    List<EventDto> getEvents();

    EventRegisteredDto register(Integer eventId, Integer studentId) throws AlreadyRegisteredException;
}
