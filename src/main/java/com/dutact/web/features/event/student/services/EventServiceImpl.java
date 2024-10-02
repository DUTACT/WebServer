package com.dutact.web.features.event.student.services;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.features.event.student.dtos.EventDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository,
                            EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public Optional<EventDto> getEvent(Integer id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent() && event.get().getStatus() instanceof EventStatus.Approved) {
            return event.map(eventMapper::toDto);
        }

        return Optional.empty();
    }

    @Override
    public List<EventDto> getEvents() {
        return eventRepository
                .findAllByStatus(EventStatus.Approved.TYPE_NAME)
                .stream().map(eventMapper::toDto)
                .collect(Collectors.toList());
    }
}
