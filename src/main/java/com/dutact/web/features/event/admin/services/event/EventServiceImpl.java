package com.dutact.web.features.event.admin.services.event;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.core.entities.event.CannotChangeStatusException;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.core.repositories.OrganizerRepository;
import com.dutact.web.core.specs.EventSpecs;
import com.dutact.web.features.event.admin.dtos.event.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.event.EventUpdateDto;
import com.dutact.web.storage.StorageService;
import com.dutact.web.storage.UploadFileResult;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("organizerEventService")
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final UploadedFileMapper uploadedFileMapper;
    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final StorageService storageService;

    public EventServiceImpl(EventMapper eventMapper,
                            UploadedFileMapper uploadedFileMapper,
                            EventRepository eventRepository,
                            OrganizerRepository organizerRepository,
                            StorageService storageService) {
        this.eventMapper = eventMapper;
        this.uploadedFileMapper = uploadedFileMapper;
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.storageService = storageService;
    }

    @Override
    public EventDto createEvent(Integer organizerId, EventCreateDto eventDto)
            throws NotExistsException, ConflictException {
        Event event = eventMapper.toEvent(eventDto);
        event.setOrganizer(organizerRepository.findById(organizerId)
                .orElseThrow(() -> new NotExistsException("Organizer not found")));

        UploadFileResult uploadFileResult = writeFile(eventDto.getCoverPhoto());
        event.setCoverPhoto(uploadedFileMapper.toUploadedFile(uploadFileResult));
        

        if (SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            updateEventStatus(event, new EventStatus.Approved());
        } else {
            updateEventStatus(event, new EventStatus.Pending());
        }

        eventRepository.save(event);

        return eventMapper.toEventDto(event);
    }

    @Override
    public List<EventDto> getEvents() {
        Specification<Event> spec = EventSpecs.orderByCreatedAt(false);

        return eventRepository.findAll(spec)
                .stream()
                .map(eventMapper::toEventDto)
                .toList();
    }

    @Override
    public List<EventDto> getEvents(Integer orgId) throws NotExistsException {
        if (!organizerRepository.existsById(orgId)) {
            throw new NotExistsException("Organizer not found");
        }

        Specification<Event> spec = EventSpecs.hasOrganizerId(orgId);
        spec = spec.and(EventSpecs.orderByCreatedAt(false));

        return eventRepository.findAll(spec)
                .stream()
                .map(eventMapper::toEventDto)
                .toList();
    }

    @Override
    public Optional<EventDto> getEvent(Integer eventId) {
        return eventRepository.findById(eventId).map(eventMapper::toEventDto);
    }

    @Override
    @SneakyThrows
    public EventDto updateEvent(Integer eventId, EventUpdateDto eventDto)
            throws NotExistsException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotExistsException("Event not found"));
        eventMapper.updateEvent(event, eventDto);

        if (eventDto.getCoverPhoto() != null) {
            UploadFileResult uploadFileResult = writeFile(eventDto.getCoverPhoto());
            event.setCoverPhoto(uploadedFileMapper.toUploadedFile(uploadFileResult));
        } else {
            event.setCoverPhoto(null);
        }

        eventRepository.save(event);

        return eventMapper.toEventDto(event);
    }

    @Override
    public EventStatus approveEvent(Integer eventId) throws NotExistsException, ConflictException {
        return updateEventStatus(eventId, new EventStatus.Approved());
    }

    @Override
    public EventStatus rejectEvent(Integer eventId, String reason) throws NotExistsException, ConflictException {
        return updateEventStatus(eventId, new EventStatus.Rejected(reason));
    }

    @Override
    public EventDto closeEventRegistration(Integer eventId) throws NotExistsException, ConflictException {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            throw new NotExistsException("Event not found");
        }

        if (!(eventOpt.get().getStatus() instanceof EventStatus.Approved)) {
            throw new ConflictException("Cannot close registration for unapproved event");
        }

        Event event = eventOpt.get();
        event.setEndRegistrationAt(LocalDateTime.now());

        eventRepository.save(event);

        return eventMapper.toEventDto(event);
    }

    @Override
    public void deleteEvent(Integer eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public boolean eventExists(Integer orgId, Integer eventId) {
        return eventRepository.existsByIdAndOrganizerId(eventId, orgId);
    }

    private EventStatus updateEventStatus(Integer eventId, EventStatus eventStatus)
            throws NotExistsException, ConflictException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotExistsException("Event not found"));
        updateEventStatus(event, eventStatus);
        eventRepository.save(event);

        return event.getStatus();
    }

    private void updateEventStatus(Event event, EventStatus status) throws ConflictException {
        try {
            event.setStatus(status);
        } catch (CannotChangeStatusException e) {
            throw new ConflictException("Cannot change status");
        }
    }

    private void updateFile(MultipartFile file, String fileId) {
        try (var inputStream = file.getInputStream()) {
            storageService.updateFile(fileId, inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private UploadFileResult writeFile(MultipartFile file) {
        try (var inputStream = file.getInputStream()) {
            return storageService.uploadFile(inputStream,
                    FilenameUtils.getExtension(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
