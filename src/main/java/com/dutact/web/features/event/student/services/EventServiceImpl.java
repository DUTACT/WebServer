package com.dutact.web.features.event.student.services;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.EventFollow;
import com.dutact.web.core.entities.EventRegistration;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.repositories.EventFollowRepository;
import com.dutact.web.core.repositories.EventRegistrationRepository;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.core.specs.EventRegistrationSpecs;
import com.dutact.web.core.specs.EventSpecs;
import com.dutact.web.features.event.student.dtos.EventDto;
import com.dutact.web.features.event.student.dtos.EventFollowDto;
import com.dutact.web.features.event.student.dtos.EventRegisteredDto;
import com.dutact.web.features.event.student.services.exceptions.FollowForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.RegisterForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.UnfollowForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.UnregisterForbiddenException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final StudentAccountService studentAccountService;
    private final EventFollowRepository eventFollowRepository;
    private final EventMapper eventMapper;
    private final EventRegistrationMapper eventRegistrationMapper;
    private final EventFollowMapper eventFollowMapper;

    public EventServiceImpl(EventRepository eventRepository,
                            EventRegistrationRepository eventRegistrationRepository,
                            StudentAccountService studentAccountService,
                            EventMapper eventMapper,
                            EventRegistrationMapper eventRegistrationMapper,
                            EventFollowRepository eventFollowRepository,
                            EventFollowMapper eventFollowMapper
    ) {
        this.eventRepository = eventRepository;
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.studentAccountService = studentAccountService;
        this.eventMapper = eventMapper;
        this.eventRegistrationMapper = eventRegistrationMapper;
        this.eventFollowRepository = eventFollowRepository;
        this.eventFollowMapper = eventFollowMapper;
    }

    @Override
    public Optional<EventDto> getEvent(Integer id) {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new IllegalStateException("Student not found"));

        Optional<Event> event = eventRepository.findOne(EventSpecs
                .hasId(id)
                .and(EventSpecs.hasStatus(EventStatus.Approved.TYPE_NAME))
                .and(EventSpecs.joinOrganizer()));

        if (event.isPresent() && event.get().getStatus() instanceof EventStatus.Approved) {
            Optional<EventRegistration> eventRegistration = eventRegistrationRepository
                    .findOne(EventRegistrationSpecs.hasEventId(id)
                            .and(EventRegistrationSpecs.hasStudentId(requestStudentId)));

            EventDto eventDto = eventMapper.toDto(event.get());
            eventDto.setRegisteredAt(eventRegistration.map(EventRegistration::getRegisteredAt)
                    .orElse(null));

            return Optional.of(eventDto);
        }

        return Optional.empty();
    }

    @Override
    public List<EventDto> getEvents() {
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new IllegalStateException("Student not found"));

        List<Event> events = eventRepository
                .findAll(EventSpecs.hasStatus(EventStatus.Approved.TYPE_NAME)
                        .and(EventSpecs.joinOrganizer()));

        Map<Integer, EventRegistration> eventRegistrations = eventRegistrationRepository
                .findAll(EventRegistrationSpecs.hasStudentId(requestStudentId))
                .stream()
                .collect(Collectors.toMap(r -> r.getEvent().getId(), r -> r));

        return events.stream()
                .map(event -> {
                    EventDto eventDto = eventMapper.toDto(event);
                    if (eventRegistrations.containsKey(event.getId())) {
                        eventDto.setRegisteredAt(eventRegistrations
                                .get(event.getId()).getRegisteredAt());
                    }
                    return eventDto;
                })
                .toList();
    }

    @Override
    public EventRegisteredDto register(Integer eventId, Integer studentId)
            throws RegisterForbiddenException, NotExistsException {
        Optional<Event> eventOpt = eventRepository
                .findOne(EventSpecs.hasId(eventId)
                        .and(EventSpecs.hasStatus(EventStatus.Approved.TYPE_NAME)));

        if (eventOpt.isEmpty()) {
            throw new NotExistsException("Event not found.");
        }

        Event event = eventOpt.get();
        if (event.getEndRegistrationAt().isBefore(LocalDateTime.now())) {
            throw new RegisterForbiddenException("Registration is closed.");
        }

        Optional<EventRegistration> registrationOpt = eventRegistrationRepository
                .findOne(EventRegistrationSpecs.hasEventId(eventId)
                        .and(EventRegistrationSpecs.hasStudentId(studentId)));

        if (registrationOpt.isPresent()) {
            throw new RegisterForbiddenException("You are already registered for this event.");
        }


        EventRegistration eventRegistration = new EventRegistration();
        eventRegistration.setEvent(new Event(eventId));
        eventRegistration.setStudent(new Student(studentId));

        return eventRegistrationMapper.toDto(eventRegistrationRepository.save(eventRegistration));
    }

    @Override
    public void unregister(Integer eventId, Integer studentId)
            throws UnregisterForbiddenException, NotExistsException {
        if (!eventRegistrationRepository.existsByEventIdAndStudentId(eventId, studentId)) {
            throw new UnregisterForbiddenException("You are not registered for this event.");
        }

        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException();
        }

        eventRegistrationRepository.deleteByEventIdAndStudentId(eventId, studentId);
    }

    @Override
    public EventFollowDto follow(Integer eventId, Integer studentId) throws FollowForbiddenException, NotExistsException {
        if (eventFollowRepository.existsByEventIdAndStudentId(eventId, studentId)) {
            throw new FollowForbiddenException("You are already following this event.");
        }

        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException();
        }

        EventFollow eventFollow = new EventFollow();
        eventFollow.setEvent(new Event(eventId));
        eventFollow.setStudent(new Student(studentId));

        return eventFollowMapper.toDto(eventFollowRepository.save(eventFollow));
    }

    @Override
    public void unfollow(Integer eventId, Integer studentId)
            throws UnfollowForbiddenException, NotExistsException {
        if (!eventFollowRepository.existsByEventIdAndStudentId(eventId, studentId)) {
            throw new UnfollowForbiddenException("You are not following this event.");
        }

        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException();
        }

        eventFollowRepository.deleteByEventIdAndStudentId(eventId, studentId);
    }
}
