package com.dutact.web.features.event.student.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.EventRegistration;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.repositories.EventRegistrationRepository;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.features.event.student.dtos.EventDto;
import com.dutact.web.features.event.student.dtos.EventRegisteredDto;
import com.dutact.web.features.event.student.services.exceptions.AlreadyRegisteredException;
import com.dutact.web.features.event.student.services.exceptions.NotRegisteredException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventMapper eventMapper;
    private final EventRegistrationMapper eventRegistrationMapper;

    public EventServiceImpl(EventRepository eventRepository,
                            EventRegistrationRepository eventRegistrationRepository,
                            EventMapper eventMapper,
                            EventRegistrationMapper eventRegistrationMapper) {
        this.eventRepository = eventRepository;
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventMapper = eventMapper;
        this.eventRegistrationMapper = eventRegistrationMapper;
    }

    @Override
    public Optional<EventDto> getEvent(Integer id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent() && event.get().getStatus() instanceof EventStatus.Approved) {
            return event.map(eventMapper::toDto);
        }

        return Optional.empty();
    }

    @Override
    public List<EventDto> getEvents() {
        return eventRepository
                .findAllByStatus(EventStatus.Approved.TYPE_NAME)
                .stream().map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRegisteredDto register(Integer eventId, Integer studentId)
            throws AlreadyRegisteredException, NotExistsException {
        if (eventRegistrationRepository.existsByEventIdAndStudentId(eventId, studentId)) {
            throw new AlreadyRegisteredException();
        }

        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException();
        }

        EventRegistration eventRegistration = new EventRegistration();
        eventRegistration.setEvent(new Event(eventId));
        eventRegistration.setStudent(new Student(studentId));

        return eventRegistrationMapper.toDto(eventRegistrationRepository.save(eventRegistration));
    }

    @Override
    public void unregister(Integer eventId, Integer studentId)
            throws NotRegisteredException, NotExistsException {
        if (!eventRegistrationRepository.existsByEventIdAndStudentId(eventId, studentId)) {
            throw new NotRegisteredException();
        }

        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException();
        }

        eventRegistrationRepository.deleteByEventIdAndStudentId(eventId, studentId);
    }
}
