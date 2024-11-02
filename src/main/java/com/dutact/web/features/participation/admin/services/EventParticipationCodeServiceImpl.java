package com.dutact.web.features.participation.admin.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.repositories.EventParticipationCodeRepository;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.core.specs.EventParticipationCodeSpecs;
import com.dutact.web.features.participation.admin.dtos.CreateEventParticipationCodeDto;
import com.dutact.web.features.participation.admin.dtos.EventParticipationCodeDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EventParticipationCodeServiceImpl implements EventParticipationCodeService {
    private final EventParticipationCodeRepository participationCodeRepository;
    private final EventRepository eventRepository;
    private final EventParticipationCodeMapper mapper;

    public EventParticipationCodeServiceImpl(
            EventParticipationCodeRepository participationCodeRepository,
            EventRepository eventRepository,
            EventParticipationCodeMapper mapper) {
        this.participationCodeRepository = participationCodeRepository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Override
    public EventParticipationCodeDto createEventParticipationCode(
            Integer eventId,
            CreateEventParticipationCodeDto createEventParticipationCodeDto)
            throws NotExistsException {
        var event = eventRepository.findById(eventId).orElseThrow(() -> new NotExistsException("Event not found"));

        var eventParticipationCode = mapper.toEventParticipationCode(createEventParticipationCodeDto);
        eventParticipationCode.setEvent(event);

        var savedEventParticipationCode = participationCodeRepository.save(eventParticipationCode);
        return mapper.toDto(savedEventParticipationCode);
    }

    @Override
    public EventParticipationCodeDto getEventParticipationCode(Integer id) throws NotExistsException {
        var participationCode = participationCodeRepository
                .findById(id)
                .orElseThrow(() -> new NotExistsException("Event participation code not found"));

        return mapper.toDto(participationCode);
    }

    @Override
    public Collection<EventParticipationCodeDto> getEventParticipationCodes(Integer eventId) throws NotExistsException {
        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException("Event not found");
        }

        return participationCodeRepository
                .findAll(EventParticipationCodeSpecs.hasEventId(eventId))
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void deleteEventParticipationCode(Integer id) {
        participationCodeRepository.deleteById(id);
    }
}
