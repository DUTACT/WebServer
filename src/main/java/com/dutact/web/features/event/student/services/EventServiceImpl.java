package com.dutact.web.features.event.student.services;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.EventFollow;
import com.dutact.web.core.entities.eventregistration.EventRegistration;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.repositories.*;
import com.dutact.web.core.specs.EventCheckInSpecs;
import com.dutact.web.core.specs.EventFollowSpecs;
import com.dutact.web.core.specs.EventRegistrationSpecs;
import com.dutact.web.core.specs.EventSpecs;
import com.dutact.web.features.checkin.admin.services.EventCheckInMapper;
import com.dutact.web.features.event.student.dtos.*;
import com.dutact.web.features.event.student.services.exceptions.FollowForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.RegisterForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.UnfollowForbiddenException;
import com.dutact.web.features.event.student.services.exceptions.UnregisterForbiddenException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final StudentRepository studentRepository;
    private final EventCheckInRepository checkInRepository;
    private final EventCheckInMapper eventCheckInMapper;

    public EventServiceImpl(EventRepository eventRepository,
                            EventRegistrationRepository eventRegistrationRepository,
                            StudentAccountService studentAccountService,
                            EventMapper eventMapper,
                            EventCheckInMapper eventCheckInMapper,
                            EventCheckInRepository eventCheckInRepository,
                            EventRegistrationMapper eventRegistrationMapper,
                            EventFollowRepository eventFollowRepository,
                            StudentRepository studentRepository,
                            EventFollowMapper eventFollowMapper
    ) {
        this.eventRepository = eventRepository;
        this.eventCheckInMapper = eventCheckInMapper;
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.studentAccountService = studentAccountService;
        this.eventMapper = eventMapper;
        this.checkInRepository = eventCheckInRepository;
        this.eventRegistrationMapper = eventRegistrationMapper;
        this.eventFollowRepository = eventFollowRepository;
        this.studentRepository = studentRepository;
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
            EventDto eventDto = eventMapper.toDto(event.get());

            Optional<EventRegistration> eventRegistration = eventRegistrationRepository
                    .findOne(EventRegistrationSpecs.hasEventId(id)
                            .and(EventRegistrationSpecs.hasStudentId(requestStudentId)));
            eventDto.setRegisteredAt(eventRegistration.map(EventRegistration::getRegisteredAt)
                    .orElse(null));

            Optional<EventFollow> eventFollow = eventFollowRepository
                    .findOne(EventFollowSpecs.hasEventId(id)
                            .and(EventFollowSpecs.hasStudentId(requestStudentId)));
            eventDto.setFollowedAt(eventFollow.map(EventFollow::getFollowedAt)
                    .orElse(null));


            int numberFollower = eventFollowRepository.countByEventId(event.get().getId());
            int numberRegister = eventRegistrationRepository.countByEventId(event.get().getId());
            eventDto.setRegisterNumber(numberRegister);
            eventDto.setFollowerNumber(numberFollower);

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

        Map<Integer, EventFollow> eventFollows = eventFollowRepository
                .findAll(EventFollowSpecs.hasStudentId(requestStudentId))
                .stream()
                .collect(Collectors.toMap(f -> f.getEvent().getId(), f -> f));

        return events.stream()
                .map(event -> {
                    EventDto eventDto = eventMapper.toDto(event);
                    if (eventRegistrations.containsKey(event.getId())) {
                        eventDto.setRegisteredAt(eventRegistrations
                                .get(event.getId()).getRegisteredAt());
                    }

                    if (eventFollows.containsKey(event.getId())) {
                        eventDto.setFollowedAt(eventFollows
                                .get(event.getId()).getFollowedAt());
                    }

                    int numberFollower = eventFollowRepository.countByEventId(event.getId());
                    int numberRegister = eventRegistrationRepository.countByEventId(event.getId());
                    eventDto.setRegisterNumber(numberRegister);
                    eventDto.setFollowerNumber(numberFollower);
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

    @Override
    public PageResponse<EventDetailsDto> getRegisteredEvents(
            Integer studentId, Integer page, Integer pageSize) {
        // Verify student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create pageable request
        Pageable pageable = PageRequest.of(page - 1, pageSize, 
                Sort.by(Sort.Direction.ASC, "event.createdAt"));
        
        // Get paginated registrations (excluding rejected events)
        Page<EventRegistration> registeredEventsPage = 
                eventRegistrationRepository.findAllByStudentId(studentId, pageable);

        List<EventRegistration> filteredEvent = registeredEventsPage.stream()
                .filter(e -> !(e.getEvent().getStatus() instanceof EventStatus.Rejected))
                .toList();
        registeredEventsPage = new PageImpl<>(
            filteredEvent,
            registeredEventsPage.getPageable(),
            filteredEvent.size()
        );

        // Map to DTOs using the existing page
        return PageResponse.of(
                registeredEventsPage,
                registration -> {
                    var checkIns = checkInRepository.findAll(
                            EventCheckInSpecs.hasStudent(studentId)
                                    .and(EventCheckInSpecs.hasEventId(registration.getEvent().getId())));
                    EventDto eventDto = this.getEvent(registration.getEvent().getId()).get();
                    EventDetailsDto detailsDto = eventMapper.toDetailsDto(registration);
                    detailsDto.setEvent(eventDto);
                    detailsDto.setTotalCheckIn(checkIns.size());
                    detailsDto.setCertificateStatus(registration.getCertificateStatus());
                    detailsDto.setCheckIns(checkIns.stream()
                            .map(eventCheckInMapper::toCheckInHistoryDto)
                            .toList());
                    return detailsDto;
                }
        );
    }

    @Override
    public PageResponse<EventDetailsDto> getFollowedEvents(Integer studentId, Integer page, Integer pageSize) {
        // Verify student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create pageable request
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "event.createdAt"));
        
        // Get paginated follows
        Page<EventFollow> followedEventsPage = 
                eventFollowRepository.findAllByStudentId(studentId, pageable);

        // Map to DTOs using the existing page
        return PageResponse.of(
                followedEventsPage,
                follow -> {
                    var checkIns = checkInRepository.findAll(
                            EventCheckInSpecs.hasStudent(studentId)
                                    .and(EventCheckInSpecs.hasEventId(follow.getEvent().getId())));
                    
                    EventDto eventDto = this.getEvent(follow.getEvent().getId()).get();
                    EventDetailsDto detailsDto = eventMapper.toDetailsDto(follow);
                    detailsDto.setEvent(eventDto);
                    detailsDto.setTotalCheckIn(checkIns.size());
                    detailsDto.setCheckIns(checkIns.stream()
                            .map(eventCheckInMapper::toCheckInHistoryDto)
                            .toList());
                    return detailsDto;
                }
        );
    }

}
