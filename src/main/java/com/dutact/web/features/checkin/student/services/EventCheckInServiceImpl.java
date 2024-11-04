package com.dutact.web.features.checkin.student.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.EventCheckIn;
import com.dutact.web.core.repositories.EventCheckInCodeRepository;
import com.dutact.web.core.repositories.EventCheckInRepository;
import com.dutact.web.core.repositories.StudentRepository;
import com.dutact.web.core.specs.EventCheckInSpecs;
import com.dutact.web.features.checkin.student.dtos.EventCheckInResult;
import com.dutact.web.features.checkin.student.services.exceptions.AlreadyCheckInException;
import com.dutact.web.features.checkin.student.services.exceptions.EarlyCheckInAttemptException;
import com.dutact.web.features.checkin.student.services.exceptions.LateCheckInAttemptException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventCheckInServiceImpl implements EventCheckInService {
    private final EventCheckInCodeRepository checkInCodeRepository;
    private final EventCheckInRepository checkInRepository;
    private final StudentRepository studentRepository;

    public EventCheckInServiceImpl(EventCheckInCodeRepository checkInCodeRepository,
                                   EventCheckInRepository checkInRepository,
                                   StudentRepository studentRepository) {
        this.checkInCodeRepository = checkInCodeRepository;
        this.checkInRepository = checkInRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public EventCheckInResult checkIn(String code, Integer studentId)
            throws EarlyCheckInAttemptException,
            AlreadyCheckInException,
            LateCheckInAttemptException,
            NotExistsException {
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

        var checkIn = new EventCheckIn();
        checkIn.setStudent(student);
        checkIn.setCheckInTime(now);
        checkIn.setCheckInCode(checkInCode);

        checkInRepository.save(checkIn);

        var result = new EventCheckInResult();
        result.setCheckInTime(now);

        return result;
    }
}
