package com.dutact.web.service.checkin.admin;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.data.repository.EventCheckInCodeRepository;
import com.dutact.web.data.repository.EventRepository;
import com.dutact.web.dto.checkin.admin.CreateEventCheckInCodeDto;
import com.dutact.web.dto.checkin.admin.EventCheckInCodeDto;
import com.dutact.web.specs.EventCheckInCodeSpecs;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class EventCheckInCodeServiceImpl implements EventCheckInCodeService {
    private final EventCheckInCodeRepository checkInCodeRepository;
    private final EventRepository eventRepository;
    private final EventCheckInCodeMapper mapper;

    public EventCheckInCodeServiceImpl(
            EventCheckInCodeRepository checkInCodeRepository,
            EventRepository eventRepository,
            EventCheckInCodeMapper mapper) {
        this.checkInCodeRepository = checkInCodeRepository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Override
    public EventCheckInCodeDto createCheckInCode(
            CreateEventCheckInCodeDto createEventCheckinCodeDto)
            throws NotExistsException {
        var eventId = createEventCheckinCodeDto.getEventId();
        var event = eventRepository.findById(eventId).orElseThrow(() -> new NotExistsException("Event not found"));

        var checkInCode = mapper.toCheckInCode(createEventCheckinCodeDto);
        checkInCode.setEvent(event);

        var savedCheckInCode = checkInCodeRepository.save(checkInCode);
        return mapper.toDto(savedCheckInCode);
    }

    @Override
    public EventCheckInCodeDto getCheckInCode(UUID id) throws NotExistsException {
        var checkInCode = checkInCodeRepository
                .findById(id)
                .orElseThrow(() -> new NotExistsException("Event participation code not found"));

        return mapper.toDto(checkInCode);
    }

    @Override
    public Collection<EventCheckInCodeDto> getCheckInCodes(Integer eventId) throws NotExistsException {
        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException("Event not found");
        }

        return checkInCodeRepository
                .findAll(EventCheckInCodeSpecs.hasEventId(eventId))
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void deleteCheckInCode(UUID id) {
        checkInCodeRepository.deleteById(id);
    }
}
