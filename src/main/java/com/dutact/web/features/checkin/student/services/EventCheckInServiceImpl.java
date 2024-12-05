package com.dutact.web.features.checkin.student.services;

import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.EventCheckIn;
import com.dutact.web.core.repositories.*;
import com.dutact.web.core.specs.EventCheckInSpecs;
import com.dutact.web.features.checkin.student.dtos.EventCheckInParams;
import com.dutact.web.features.checkin.student.dtos.EventCheckInResult;
import com.dutact.web.features.checkin.student.dtos.StudentCheckInDetailDto;
import com.dutact.web.features.checkin.student.services.exceptions.AlreadyCheckInException;
import com.dutact.web.features.checkin.student.services.exceptions.EarlyCheckInAttemptException;
import com.dutact.web.features.checkin.student.services.exceptions.LateCheckInAttemptException;
import com.dutact.web.features.checkin.student.services.exceptions.OutOfRangeException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventCheckInServiceImpl implements EventCheckInService {
    private final EventCheckInCodeRepository checkInCodeRepository;
    private final EventCheckInRepository checkInRepository;
    private final StudentRepository studentRepository;
    private final StudentAccountService studentAccountService;
    private final EventRegistrationRepository registrationRepository;
    private final EventFollowRepository eventFollowRepository;
    private final EventRepository eventRepository;
    private final StudentEventMapper mapper;

    public EventCheckInServiceImpl(EventCheckInCodeRepository checkInCodeRepository,
                                   EventCheckInRepository checkInRepository,
                                   StudentRepository studentRepository,
                                   StudentAccountService studentAccountService,
                                   EventRegistrationRepository registrationRepository,
                                   EventRepository eventRepository,
                                   EventFollowRepository eventFollowRepository,
                                   StudentEventMapper mapper
    ) {
        this.checkInCodeRepository = checkInCodeRepository;
        this.checkInRepository = checkInRepository;
        this.studentRepository = studentRepository;
        this.studentAccountService = studentAccountService;
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
        this.eventFollowRepository = eventFollowRepository;
        this.mapper = mapper;
    }

    @Override
    public EventCheckInResult checkIn(EventCheckInParams params)
            throws EarlyCheckInAttemptException,
            AlreadyCheckInException,
            LateCheckInAttemptException,
            NotExistsException, OutOfRangeException {
        var studentId = params.getStudentId();
        var code = params.getCode();
        var geoPosition = params.getGeoPosition();

        UUID codeUUID;
        try {
            codeUUID = UUID.fromString(code);
        } catch (IllegalArgumentException e) {
            throw new NotExistsException("Check in code does not exist");
        }

        var checkInCode = checkInCodeRepository
                .findById(codeUUID)
                .orElseThrow(() -> new NotExistsException("Check in code does not exist"));

        var student = studentRepository
                .findById(studentId)
                .orElseThrow(() -> new NotExistsException("Student does not exist"));

        if (checkInRepository.exists(
                EventCheckInSpecs.hasCode(codeUUID)
                        .and(EventCheckInSpecs.hasStudent(studentId)))) {
            throw new AlreadyCheckInException();
        }

        var now = LocalDateTime.now();

        if (now.isBefore(checkInCode.getStartAt())) {
            throw new EarlyCheckInAttemptException();
        }

        if (now.isAfter(checkInCode.getEndAt())) {
            throw new LateCheckInAttemptException();
        }

        if (checkInCode.getLocation() != null) {
            if (geoPosition == null) {
                throw new OutOfRangeException();
            }

            var distance = checkInCode.getLocation().distance(geoPosition);
            if (distance > checkInCode.getRange()) {
                throw new NotExistsException("Out of range");
            }
        }

        var checkIn = new EventCheckIn();
        checkIn.setStudent(student);
        checkIn.setCheckInTime(now);
        checkIn.setCheckInCode(checkInCode);

        checkInRepository.save(checkIn);

        var result = new EventCheckInResult();
        result.setCheckInTime(now);
        result.setEventName(checkInCode.getEvent().getName());
        result.setEventId(checkInCode.getEvent().getId());

        return result;
    }

    @Override
    public StudentCheckInDetailDto getCheckInDetail(Integer eventId, String username) throws NotExistsException {
        return null;
    }
}
