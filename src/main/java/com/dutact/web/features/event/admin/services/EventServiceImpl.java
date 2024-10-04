package com.dutact.web.features.event.admin.services;

import com.dutact.web.core.entities.EventOrganizer;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.features.event.admin.dtos.EventCreateUpdateDto;
import com.dutact.web.features.event.admin.dtos.EventDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service("organizerEventService")
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    public EventServiceImpl(EventMapper eventMapper, EventRepository eventRepository) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
    }

    @Override
    public EventDto createEvent(Integer orgId, EventCreateUpdateDto eventDto) {
        Event event = eventMapper.toEvent(eventDto);
        event.setOrganizer(new EventOrganizer(orgId));
        eventRepository.save(event);

        return eventMapper.toEventDto(event);
    }

    @Override
    public Collection<EventDto> getEvents() {
        return eventRepository.findAll().stream().map(eventMapper::toEventDto).toList();
    }

    @Override
    public Collection<EventDto> getEvents(Integer orgId) {
        return eventRepository.findAllByOrganizerId(orgId)
                .stream().map(eventMapper::toEventDto).toList();
    }

    @Override
    public Optional<EventDto> getEvent(Integer eventId) {
        return eventRepository.findById(eventId).map(eventMapper::toEventDto);
    }

    @Override
    public EventDto updateEvent(Integer eventId, EventCreateUpdateDto eventDto) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        eventMapper.updateEvent(eventDto, event);
        eventRepository.save(event);

        return eventMapper.toEventDto(event);
    }

    @Override
    public EventStatus updateEventStatus(Integer eventId, EventStatus eventStatus) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setStatus(eventStatus);
        eventRepository.save(event);

        return event.getStatus();
    }

    @Override
    public void deleteEvent(Integer eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public boolean eventExists(Integer orgId, Integer eventId) {
        return eventRepository.existsByIdAndOrganizerId(eventId, orgId);
    }
}
