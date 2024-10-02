package com.dutact.web.features.event.student.services;

import com.dutact.web.features.event.student.dtos.EventDto;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Optional<EventDto> getEvent(Integer id);

    List<EventDto> getEvents();
}
