package com.dutact.web.service.event.admin;

import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.event.admin.*;
import com.dutact.web.data.entity.event.EventStatus;
import com.dutact.web.event.event.PendingEventStartTimeUpdatedEvent;
import com.dutact.web.event.event.PublishedEventStartTimeUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Primary
@Service("organizerEventReminderService")
@AllArgsConstructor
public class EventServiceUpdatePublisherDecorator implements EventService {
    private final EventServiceImpl delegateService;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public EventDto createEvent(Integer organizerId, EventCreateDtoV2 eventDto) throws NotExistsException, ConflictException {
        var event = delegateService.createEvent(organizerId, eventDto);

        if (event.getStatus() instanceof EventStatus.Approved) {
            var eventTimeChangedEvent = new PublishedEventStartTimeUpdatedEvent(event.getId(), null, event.getStartAt());
            publishStartTimeUpdatedEvent(eventTimeChangedEvent);
        }

        if (event.getStatus() instanceof EventStatus.Pending) {
            var eventTimeChangedEvent = new PendingEventStartTimeUpdatedEvent(event.getId(), event.getStartAt());
            publishStartTimeUpdatedPendingEvent(eventTimeChangedEvent);
        }

        return event;
    }

    @Override
    public EventDto createEvent(Integer organizerId, EventCreateDtoV1 eventDto) throws NotExistsException, ConflictException {
        var event = delegateService.createEvent(organizerId, eventDto);

        if (event.getStatus() instanceof EventStatus.Approved) {
            var eventTimeChangedEvent = new PublishedEventStartTimeUpdatedEvent(event.getId(), null, event.getStartAt());
            publishStartTimeUpdatedEvent(eventTimeChangedEvent);
        }

        if (event.getStatus() instanceof EventStatus.Pending) {
            var eventTimeChangedEvent = new PendingEventStartTimeUpdatedEvent(event.getId(), event.getStartAt());
            publishStartTimeUpdatedPendingEvent(eventTimeChangedEvent);
        }

        return event;
    }

    @Override
    public List<EventDto> getEvents() {
        return delegateService.getEvents();
    }

    @Override
    public List<EventDto> getEvents(Integer orgId) throws NotExistsException {
        return delegateService.getEvents(orgId);
    }

    @Override
    public Optional<EventDto> getEvent(Integer eventId) {
        return delegateService.getEvent(eventId);
    }

    @Override
    public List<EventChangeDto> getEventChangeHistory(Integer eventId) {
        return delegateService.getEventChangeHistory(eventId);
    }

    @Override
    public EventDto updateEvent(Integer eventId, EventUpdateDtoV2 eventDto) throws NotExistsException {
        if (eventDto.getStartAt() == null) {
            return delegateService.updateEvent(eventId, eventDto);
        }

        var oldStartAt = delegateService.getEvent(eventId).map(EventDto::getStartAt).orElse(null);

        var event = delegateService.updateEvent(eventId, eventDto);

        if (event.getStatus() instanceof EventStatus.Approved) {
            var eventTimeChangedEvent = new PublishedEventStartTimeUpdatedEvent(event.getId(), oldStartAt, event.getStartAt());
            publishStartTimeUpdatedEvent(eventTimeChangedEvent);
        }

        if (event.getStatus() instanceof EventStatus.Pending) {
            var eventTimeChangedEvent = new PendingEventStartTimeUpdatedEvent(event.getId(), event.getStartAt());
            publishStartTimeUpdatedPendingEvent(eventTimeChangedEvent);
        }

        return event;
    }

    @Override
    public EventDto updateEvent(Integer eventId, EventUpdateDtoV1 eventDto) throws NotExistsException {
        if (eventDto.getStartAt() == null) {
            return delegateService.updateEvent(eventId, eventDto);
        }

        var oldStartAt = delegateService.getEvent(eventId).map(EventDto::getStartAt).orElse(null);

        var event = delegateService.updateEvent(eventId, eventDto);

        if (event.getStatus() instanceof EventStatus.Approved) {
            var eventTimeChangedEvent = new PublishedEventStartTimeUpdatedEvent(event.getId(), oldStartAt, event.getStartAt());
            publishStartTimeUpdatedEvent(eventTimeChangedEvent);
        }

        if (event.getStatus() instanceof EventStatus.Pending) {
            var eventTimeChangedEvent = new PendingEventStartTimeUpdatedEvent(event.getId(), event.getStartAt());
            publishStartTimeUpdatedPendingEvent(eventTimeChangedEvent);
        }

        return event;
    }

    @Override
    public EventDto renewEventRegistration(Integer eventId, RenewEventRegistrationDto renewEventRegistrationDto) throws NotExistsException {
        return delegateService.renewEventRegistration(eventId, renewEventRegistrationDto);
    }

    @Override
    public EventDto changeEventTime(Integer eventId, ChangeEventTimeDto changeEventTimeDto) throws NotExistsException {
        if (changeEventTimeDto.getStartAt() == null) {
            return delegateService.changeEventTime(eventId, changeEventTimeDto);
        }

        var oldStartAt = delegateService.getEvent(eventId).map(EventDto::getStartAt).orElse(null);

        var event = delegateService.changeEventTime(eventId, changeEventTimeDto);

        if (event.getStatus() instanceof EventStatus.Approved) {
            var eventTimeChangedEvent = new PublishedEventStartTimeUpdatedEvent(event.getId(), oldStartAt, event.getStartAt());
            publishStartTimeUpdatedEvent(eventTimeChangedEvent);
        }

        if (event.getStatus() instanceof EventStatus.Pending) {
            var eventTimeChangedEvent = new PendingEventStartTimeUpdatedEvent(event.getId(), event.getStartAt());
            publishStartTimeUpdatedPendingEvent(eventTimeChangedEvent);
        }

        return event;
    }

    @Override
    public EventDto closeEventRegistration(Integer eventId) throws NotExistsException, ConflictException {
        return delegateService.closeEventRegistration(eventId);
    }

    @Override
    public EventStatus approveEvent(Integer eventId) throws NotExistsException, ConflictException {
        var startAt = delegateService.getEvent(eventId).map(EventDto::getStartAt).orElse(null);

        var eventStatus = delegateService.approveEvent(eventId);

        var eventTimeChangedEvent = new PublishedEventStartTimeUpdatedEvent(eventId, null, startAt);
        publishStartTimeUpdatedEvent(eventTimeChangedEvent);

        return eventStatus;
    }

    @Override
    public EventStatus rejectEvent(Integer eventId, String reason) throws NotExistsException, ConflictException {
        return delegateService.rejectEvent(eventId, reason);
    }

    @Override
    public void deleteEvent(Integer eventId) {
        delegateService.deleteEvent(eventId);
    }

    @Override
    public boolean eventExists(Integer orgId, Integer eventId) {
        return delegateService.eventExists(orgId, eventId);
    }

    private void publishStartTimeUpdatedEvent(PublishedEventStartTimeUpdatedEvent event) {
        try {
            eventPublisher.publishEvent(event);
        } catch (Exception e) {
            log.error("Failed to publish event time changed event", e);
        }
    }

    private void publishStartTimeUpdatedPendingEvent(PendingEventStartTimeUpdatedEvent event) {
        try {
            eventPublisher.publishEvent(event);
        } catch (Exception e) {
            log.error("Failed to publish event time changed for pending event", e);
        }
    }
}
