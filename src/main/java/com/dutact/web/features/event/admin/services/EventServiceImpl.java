package com.dutact.web.features.event.admin.services;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.features.event.admin.dtos.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.EventDto;
import com.dutact.web.features.event.admin.dtos.EventUpdateDto;
import com.dutact.web.storage.StorageService;
import com.dutact.web.storage.UploadFileResult;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service("organizerEventService")
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final UploadedFileMapper uploadedFileMapper;
    private final EventRepository eventRepository;
    private final StorageService storageService;

    public EventServiceImpl(EventMapper eventMapper,
                            UploadedFileMapper uploadedFileMapper,
                            EventRepository eventRepository,
                            StorageService storageService) {
        this.eventMapper = eventMapper;
        this.uploadedFileMapper = uploadedFileMapper;
        this.eventRepository = eventRepository;
        this.storageService = storageService;
    }

    @Override
    @SneakyThrows
    public EventDto createEvent(EventCreateDto eventDto) {
        Event event = eventMapper.toEvent(eventDto);
        UploadFileResult uploadFileResult = storageService.uploadFile(
                eventDto.getCoverPhoto().getInputStream(), FilenameUtils.getExtension(
                        eventDto.getCoverPhoto().getOriginalFilename()));
        event.setCoverPhoto(uploadedFileMapper.toUploadedFile(uploadFileResult));

        if (SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            event.setStatus(new EventStatus.Approved());
        } else {
            event.setStatus(new EventStatus.Pending());
        }

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
    public EventDto updateEvent(Integer eventId, EventUpdateDto eventDto) {
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
