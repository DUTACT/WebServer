package com.dutact.web.features.event.student.services;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.EventCheckIn;
import com.dutact.web.core.entities.EventCheckInCode;
import com.dutact.web.core.entities.EventFollow;
import com.dutact.web.core.entities.eventregistration.EventRegistration;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.entities.eventregistration.participationcert.ParticipationCertificateStatus;
import com.dutact.web.core.entities.feedback.Feedback;
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
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
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
    private final PostLikeRepository postLikeRepository;
    private final EventCheckInCodeRepository eventCheckInCodeRepository;

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
        // Get current student ID
        Integer requestStudentId = studentAccountService
                .getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new IllegalStateException("Student not found"));

        // Get all approved events with necessary joins in a single query
        List<Event> events = eventRepository.findAll(
            EventSpecs.joinOrganizer()
                .and(EventSpecs.hasStatus(EventStatus.Approved.TYPE_NAME))
                .and(EventSpecs.orderByCreatedAt(true))
        );

        // Fetch all related data in bulk to avoid N+1 queries
        List<EventRegistration> allRegistrations = eventRegistrationRepository.findAll();
        List<EventFollow> allFollows = eventFollowRepository.findAll();
        
        // Get current student's registrations and follows
        List<EventRegistration> studentRegistrations = allRegistrations.stream()
            .filter(r -> r.getStudent().getId().equals(requestStudentId))
            .toList();
        List<EventFollow> studentFollows = allFollows.stream()
            .filter(f -> f.getStudent().getId().equals(requestStudentId))
            .toList();
        
        // Transform to DTOs with all necessary data
        List<EventDto> eventDtos = events.stream()
            .map(eventMapper::toDto)
            .toList();

        // Enrich DTOs with counts and dates
        eventDtos.forEach(eventDto -> {
            Integer eventId = eventDto.getId();
            
            // Set registration and follow dates for current student
            studentRegistrations.stream()
                .filter(r -> r.getEvent().getId().equals(eventId))
                .findFirst()
                .ifPresent(reg -> eventDto.setRegisteredAt(reg.getRegisteredAt()));
                
            studentFollows.stream()
                .filter(f -> f.getEvent().getId().equals(eventId))
                .findFirst()
                .ifPresent(follow -> eventDto.setFollowedAt(follow.getFollowedAt()));
            
            // Set counts
            long registerCount = allRegistrations.stream()
                .filter(r -> r.getEvent().getId().equals(eventId))
                .count();
            long followCount = allFollows.stream()
                .filter(f -> f.getEvent().getId().equals(eventId))
                .count();
                
            eventDto.setRegisterNumber((int) registerCount);
            eventDto.setFollowerNumber((int) followCount);
        });

        return eventDtos;
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
    public PageResponse<EventDetailsDto> getRegisteredEvents(Integer studentId, Integer page, Integer pageSize) {
        // Create pageable request with proper sorting
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "event.createdAt"));
        
        // Get paginated registrations with all necessary joins and filters in a single query
        Page<EventRegistration> registeredEventsPage = eventRegistrationRepository.findAll(
            EventRegistrationSpecs.joinEvent()
                .and(EventRegistrationSpecs.joinStudent())
                .and(EventRegistrationSpecs.joinOrganizer())
                .and(EventRegistrationSpecs.hasStudentId(studentId))
                .and(EventRegistrationSpecs.hasEventStatus(EventStatus.Approved.TYPE_NAME))
                .and(EventRegistrationSpecs.orderByRegisteredAt(true)),
            pageable
        );

        // Fetch all related data in bulk to avoid N+1 queries
        List<EventRegistration> allStudentRegistrations = eventRegistrationRepository.findAllByStudentId(studentId);
        List<EventRegistration> allRegistrations = eventRegistrationRepository.findAll();
        List<EventFollow> allFollows = eventFollowRepository.findAll();
        List<EventFollow> studentFollows = eventFollowRepository.findAllByStudentId(studentId);
        List<EventCheckInCode> checkInCodes = eventCheckInCodeRepository.findAll();
        List<EventCheckIn> checkIns = checkInRepository.findAll();

        // Transform to DTOs with all necessary data
        PageResponse<EventDetailsDto> response = PageResponse.of(registeredEventsPage, eventMapper::toDetailsDto);
        
        response.getData().forEach(eventDetails -> {
            Integer eventId = eventDetails.getEvent().getId();
            
            // Set registration and follow dates
            allStudentRegistrations.stream()
                .filter(r -> r.getEvent().getId().equals(eventId))
                .findFirst()
                .ifPresent(reg -> eventDetails.getEvent().setRegisteredAt(reg.getRegisteredAt()));
                
            studentFollows.stream()
                .filter(f -> f.getEvent().getId().equals(eventId))
                .findFirst()
                .ifPresent(follow -> eventDetails.getEvent().setFollowedAt(follow.getFollowedAt()));

            // Set counts
            long registerCount = allRegistrations.stream()
                .filter(r -> r.getEvent().getId().equals(eventId))
                .count();
            long followCount = allFollows.stream()
                .filter(f -> f.getEvent().getId().equals(eventId))
                .count();
                
            eventDetails.getEvent().setRegisterNumber((int) registerCount);
            eventDetails.getEvent().setFollowerNumber((int) followCount);

            // Set check-in information
            List<EventCheckInCode> eventCheckInCodes = checkInCodes.stream()
                .filter(c -> c.getEvent().getId().equals(eventId))
                .toList();
                
            List<EventCheckIn> studentCheckIns = checkIns.stream()
                .filter(i -> eventCheckInCodes.stream()
                    .map(EventCheckInCode::getId)
                    .anyMatch(id -> id.equals(i.getCheckInCode().getId()))
                    && i.getStudent().getId().equals(studentId))
                .toList();

            eventDetails.setTotalCheckIn(studentCheckIns.size());
            eventDetails.setCheckIns(studentCheckIns.stream()
                .map(eventCheckInMapper::toCheckInHistoryDto)
                .toList());
        });

        return response;
    }

    @Override
    public PageResponse<EventDetailsDto> getFollowedEvents(Integer studentId, Integer page, Integer pageSize) {
        // Verify student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create pageable request with proper sorting
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "event.createdAt"));
        
        // Get paginated follows with all necessary joins and filters in a single query
        Page<EventFollow> followedEventsPage = eventFollowRepository.findAll(
            EventFollowSpecs.joinEvent()
                .and(EventFollowSpecs.joinStudent())
                .and(EventFollowSpecs.hasStudentId(studentId))
                .and(EventFollowSpecs.hasEventStatus(EventStatus.Approved.TYPE_NAME)),
            pageable
        );

        // Fetch all related data in bulk to avoid N+1 queries
        List<EventRegistration> allRegistrations = eventRegistrationRepository.findAll();
        List<EventFollow> allFollows = eventFollowRepository.findAll();
        List<EventRegistration> studentRegistrations = eventRegistrationRepository.findAllByStudentId(studentId);
        List<EventCheckInCode> checkInCodes = eventCheckInCodeRepository.findAll();
        List<EventCheckIn> checkIns = checkInRepository.findAll();

        // Transform to DTOs with all necessary data
        PageResponse<EventDetailsDto> response = PageResponse.of(followedEventsPage, eventMapper::toDetailsDto);
        
        response.getData().forEach(eventDetails -> {
            Integer eventId = eventDetails.getEvent().getId();
            
            // Set registration and follow dates
            studentRegistrations.stream()
                .filter(r -> r.getEvent().getId().equals(eventId))
                .findFirst()
                .ifPresent(reg -> {
                    eventDetails.getEvent().setRegisteredAt(reg.getRegisteredAt());
                    eventDetails.setCertificateStatus(reg.getCertificateStatus());
                });
                
            allFollows.stream()
                .filter(f -> f.getEvent().getId().equals(eventId) && f.getStudent().getId().equals(studentId))
                .findFirst()
                .ifPresent(follow -> eventDetails.getEvent().setFollowedAt(follow.getFollowedAt()));

            // Set counts
            long registerCount = allRegistrations.stream()
                .filter(r -> r.getEvent().getId().equals(eventId))
                .count();
            long followCount = allFollows.stream()
                .filter(f -> f.getEvent().getId().equals(eventId))
                .count();
                
            eventDetails.getEvent().setRegisterNumber((int) registerCount);
            eventDetails.getEvent().setFollowerNumber((int) followCount);

            // Set check-in information
            List<EventCheckInCode> eventCheckInCodes = checkInCodes.stream()
                .filter(c -> c.getEvent().getId().equals(eventId))
                .toList();
                
            List<EventCheckIn> studentCheckIns = checkIns.stream()
                .filter(i -> eventCheckInCodes.stream()
                    .map(EventCheckInCode::getId)
                    .anyMatch(id -> id.equals(i.getCheckInCode().getId()))
                    && i.getStudent().getId().equals(studentId))
                .toList();



            eventDetails.setTotalCheckIn(studentCheckIns.size());
            eventDetails.setCheckIns(studentCheckIns.stream()
                .map(eventCheckInMapper::toCheckInHistoryDto)
                .toList());
        });

        return response;
    }

    @Override
    public PageResponse<EventDetailsDto> getConfirmedEvents(Integer studentId, Integer page, Integer pageSize) {
        // Verify student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create pageable request with proper sorting
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "event.createdAt"));
        
        // Get paginated registrations with all necessary joins and filters in a single query
        Page<EventRegistration> registeredEventsPage = eventRegistrationRepository.findAll(
            EventRegistrationSpecs.joinEvent()
                .and(EventRegistrationSpecs.joinStudent())
                .and(EventRegistrationSpecs.hasStudentId(studentId))
                .and(EventRegistrationSpecs.hasEventStatus(EventStatus.Approved.TYPE_NAME))
                .and(EventRegistrationSpecs.hasCertificateStatus(ParticipationCertificateStatus.Confirmed.TYPE_NAME)),
            pageable
        );

        // Fetch all related data in bulk to avoid N+1 queries
        List<EventRegistration> allRegistrations = eventRegistrationRepository.findAll();
        List<EventFollow> allFollows = eventFollowRepository.findAll();
        List<EventFollow> studentFollows = eventFollowRepository.findAllByStudentId(studentId);
        List<EventCheckInCode> checkInCodes = eventCheckInCodeRepository.findAll();
        List<EventCheckIn> checkIns = checkInRepository.findAll();

        // Transform to DTOs with all necessary data
        PageResponse<EventDetailsDto> response = PageResponse.of(registeredEventsPage, eventMapper::toDetailsDto);
        
        response.getData().forEach(eventDetails -> {
            Integer eventId = eventDetails.getEvent().getId();
            
            // Set registration and follow dates
            eventDetails.getEvent().setRegisteredAt(registeredEventsPage.getContent().stream()
                .filter(r -> r.getEvent().getId().equals(eventId))
                .findFirst()
                .map(EventRegistration::getRegisteredAt)
                .orElse(null));
                
            studentFollows.stream()
                .filter(f -> f.getEvent().getId().equals(eventId))
                .findFirst()
                .ifPresent(follow -> eventDetails.getEvent().setFollowedAt(follow.getFollowedAt()));

            // Set counts
            long registerCount = allRegistrations.stream()
                .filter(r -> r.getEvent().getId().equals(eventId))
                .count();
            long followCount = allFollows.stream()
                .filter(f -> f.getEvent().getId().equals(eventId))
                .count();
                
            eventDetails.getEvent().setRegisterNumber((int) registerCount);
            eventDetails.getEvent().setFollowerNumber((int) followCount);

            // Set check-in information
            List<EventCheckInCode> eventCheckInCodes = checkInCodes.stream()
                .filter(c -> c.getEvent().getId().equals(eventId))
                .toList();
                
            List<EventCheckIn> studentCheckIns = checkIns.stream()
                .filter(i -> eventCheckInCodes.stream()
                    .map(EventCheckInCode::getId)
                    .anyMatch(id -> id.equals(i.getCheckInCode().getId()))
                    && i.getStudent().getId().equals(studentId))
                .toList();

            eventDetails.setTotalCheckIn(studentCheckIns.size());
            eventDetails.setCheckIns(studentCheckIns.stream()
                .map(eventCheckInMapper::toCheckInHistoryDto)
                .toList());
        });

        return response;
    }

}
