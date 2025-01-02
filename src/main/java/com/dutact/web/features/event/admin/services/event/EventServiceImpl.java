package com.dutact.web.features.event.admin.services.event;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.core.entities.common.UploadedFile;
import com.dutact.web.core.entities.event.CannotChangeStatusException;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.entities.eventchange.EventChange;
import com.dutact.web.core.entities.eventchange.details.EventTimeChanged;
import com.dutact.web.core.entities.eventchange.details.RegistrationClosed;
import com.dutact.web.core.entities.eventchange.details.RegistrationRenewed;
import com.dutact.web.core.repositories.EventChangeRepository;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.core.repositories.OrganizerRepository;
import com.dutact.web.core.specs.EventChangeSpecs;
import com.dutact.web.core.specs.EventSpecs;
import com.dutact.web.features.event.admin.dtos.event.*;
import com.dutact.web.storage.StorageService;
import com.dutact.web.storage.UploadFileResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service("organizerEventService")
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final EventChangeMapper eventChangeMapper;
    private final UploadedFileMapper uploadedFileMapper;
    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final EventChangeRepository eventChangeRepository;
    private final StorageService storageService;

    @Override
    public EventDto createEvent(Integer organizerId, EventCreateDtoV2 eventDto)
            throws NotExistsException, ConflictException {
        Event event = eventMapper.toEvent(eventDto);
        event.setOrganizer(organizerRepository.findById(organizerId)
                .orElseThrow(() -> new NotExistsException("Organizer not found")));

        if (eventDto.getCoverPhotos() == null) {
            eventDto.setCoverPhotos(new ArrayList<>());
        }
        for (MultipartFile coverPhoto : eventDto.getCoverPhotos()) {
            UploadFileResult uploadFileResult = storageService.uploadFile(coverPhoto, FilenameUtils.getExtension(coverPhoto.getOriginalFilename()));
            event.getCoverPhotos().add(uploadedFileMapper.toUploadedFile(uploadFileResult));
        }


        if (SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            updateEventStatus(event, new EventStatus.Approved());
        } else {
            updateEventStatus(event, new EventStatus.Pending());
        }

        eventRepository.save(event);

        return eventMapper.toEventDto(event);
    }

    @Override
    public EventDto createEvent(Integer organizerId, EventCreateDtoV1 eventDto)
            throws NotExistsException, ConflictException {
        Event event = eventMapper.toEvent(eventDto);
        event.setOrganizer(organizerRepository.findById(organizerId)
                .orElseThrow(() -> new NotExistsException("Organizer not found")));

        UploadFileResult uploadFileResult = storageService.uploadFile(eventDto.getCoverPhoto(), FilenameUtils.getExtension(eventDto.getCoverPhoto().getOriginalFilename()));
        event.getCoverPhotos().add(uploadedFileMapper.toUploadedFile(uploadFileResult));

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
    public List<EventChangeDto> getEventChangeHistory(Integer eventId) {
        return eventChangeRepository.findAll(EventChangeSpecs.hasEventId(eventId))
                .stream()
                .map(eventChangeMapper::toDto)
                .toList();
    }

    @Override
    @SneakyThrows
    public EventDto updateEvent(Integer eventId, EventUpdateDtoV2 eventDto)
            throws NotExistsException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotExistsException("Event not found"));
        eventMapper.updateEvent(event, eventDto);

        updateCoverPhoto(event, eventDto);

        eventRepository.save(event);

        return eventMapper.toEventDto(event);
    }

    @Override
    @SneakyThrows
    public EventDto updateEvent(Integer eventId, EventUpdateDtoV1 eventDto) throws NotExistsException {
        Event event = eventRepository.findById(eventId).orElseThrow();
        eventMapper.updateEvent(event, eventDto);
        updateCoverPhoto(event, eventDto);
        eventRepository.save(event);
        return eventMapper.toEventDto(event);
    }

    void updateCoverPhoto(Event event, EventUpdateDtoV2 eventUpdateDtoV2) {
        if (eventUpdateDtoV2.getCoverPhotos() == null) {
            eventUpdateDtoV2.setCoverPhotos(new ArrayList<>());
        }
        var len = event.getCoverPhotos().size();
        var urlsNeedToDelete = new ArrayList<String>();
        for (int i = 0; i < len; i++) {
            if (!eventUpdateDtoV2.getKeepCoverPhotoUrls().contains(event.getCoverPhotos().get(i).getFileUrl())) {
                urlsNeedToDelete.add(event.getCoverPhotos().get(i).getFileUrl());
            }
        }
        for (String url : urlsNeedToDelete) {
            for (UploadedFile coverPhoto : event.getCoverPhotos()) {
                storageService.deleteFile(coverPhoto.getFileId());
                if (coverPhoto.getFileUrl().equals(url)) {
                    event.getCoverPhotos().remove(coverPhoto);
                    break;
                }
            }
        }
        for (MultipartFile coverPhoto : eventUpdateDtoV2.getCoverPhotos()) {
            var uploadFileResult = storageService.uploadFile(coverPhoto, FilenameUtils.getExtension(coverPhoto.getOriginalFilename()));
            event.getCoverPhotos().add(uploadedFileMapper.toUploadedFile(uploadFileResult));
        }
    }

    void updateCoverPhoto(Event event, EventUpdateDtoV1 eventUpdateDtoV1) {
        event.setCoverPhotos(new ArrayList<>());
        if (eventUpdateDtoV1.getCoverPhoto() != null) {
            var uploadFileResult = storageService.uploadFile(eventUpdateDtoV1.getCoverPhoto(), FilenameUtils.getExtension(eventUpdateDtoV1.getCoverPhoto().getOriginalFilename()));
            event.getCoverPhotos().add(uploadedFileMapper.toUploadedFile(uploadFileResult));
        }
    }
    @Override
    @Transactional
    public EventDto renewEventRegistration(
            Integer eventId,
            RenewEventRegistrationDto renewEventRegistrationDto)
            throws NotExistsException {
        var eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            throw new NotExistsException("Event not found");
        }

        var event = eventOpt.get();

        var change = new EventChange();
        change.setDetails(new RegistrationRenewed(
                event.getEndRegistrationAt(),
                renewEventRegistrationDto.getEndRegistrationAt()
        ));
        change.setChangedAt(LocalDateTime.now());
        eventChangeRepository.save(change);

        event.setEndRegistrationAt(renewEventRegistrationDto.getEndRegistrationAt());
        eventRepository.save(event);

        return eventMapper.toEventDto(event);
    }

    @Override
    @Transactional
    public EventDto changeEventTime(
            Integer eventId,
            ChangeEventTimeDto changeEventTimeDto)
            throws NotExistsException {
        var eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            throw new NotExistsException("Event not found");
        }

        var event = eventOpt.get();

        var eventChange = new EventChange();
        var changeDetails = new EventTimeChanged();

        if (changeEventTimeDto.getStartAt() != null) {
            var startChange = new EventTimeChanged.StartTimeChange();
            startChange.setNewStart(changeEventTimeDto.getStartAt());
            startChange.setOldStart(event.getStartAt());

            changeDetails.setStartTimeChange(startChange);
            event.setStartAt(changeEventTimeDto.getStartAt());
        }

        if (changeEventTimeDto.getEndAt() != null) {
            var endChange = new EventTimeChanged.EndTimeChange();
            endChange.setNewEnd(changeEventTimeDto.getEndAt());
            endChange.setOldEnd(event.getEndAt());

            changeDetails.setEndTimeChange(endChange);
            event.setEndAt(changeEventTimeDto.getEndAt());
        }

        if (changeEventTimeDto.getStartRegistrationAt() != null) {
            var startRegistrationChange = new EventTimeChanged.RegistrationStartTimeChange();
            startRegistrationChange.setNewStartRegistration(changeEventTimeDto.getStartRegistrationAt());
            startRegistrationChange.setOldStartRegistration(event.getStartRegistrationAt());

            changeDetails.setRegistrationStartChange(startRegistrationChange);
            event.setStartRegistrationAt(changeEventTimeDto.getStartRegistrationAt());
        }

        if (changeEventTimeDto.getEndRegistrationAt() != null) {
            var endRegistrationChange = new EventTimeChanged.RegistrationEndTimeChange();
            endRegistrationChange.setNewEndRegistration(changeEventTimeDto.getEndRegistrationAt());
            endRegistrationChange.setOldEndRegistration(event.getEndRegistrationAt());

            changeDetails.setRegistrationEndChange(endRegistrationChange);
            event.setEndRegistrationAt(changeEventTimeDto.getEndRegistrationAt());
        }

        eventChange.setDetails(changeDetails);
        eventChange.setChangedAt(LocalDateTime.now());

        eventChangeRepository.save(eventChange);
        eventRepository.save(event);

        return eventMapper.toEventDto(event);
    }

    @Override
    @Transactional
    public EventDto closeEventRegistration(Integer eventId) throws NotExistsException, ConflictException {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            throw new NotExistsException("Event not found");
        }

        if (!(eventOpt.get().getStatus() instanceof EventStatus.Approved)) {
            throw new ConflictException("Cannot close registration for unapproved event");
        }

        Event event = eventOpt.get();
        var newTime = LocalDateTime.now();

        var eventChange = new EventChange();
        eventChange.setEvent(event);
        eventChange.setDetails(new RegistrationClosed(
                newTime,
                event.getEndRegistrationAt()
        ));
        eventChange.setChangedAt(newTime);
        eventChangeRepository.save(eventChange);

        event.setEndRegistrationAt(newTime);
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

    private UploadFileResult writeFile(MultipartFile file) {
        try (var inputStream = file.getInputStream()) {
            return storageService.uploadFile(inputStream,
                    FilenameUtils.getExtension(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
