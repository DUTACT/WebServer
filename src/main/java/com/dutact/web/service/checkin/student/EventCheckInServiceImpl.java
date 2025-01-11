package com.dutact.web.service.checkin.student;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.utils.GeoUtils;
import com.dutact.web.controller.checkin.student.EventCheckInParams;
import com.dutact.web.controller.checkin.student.EventCheckInResult;
import com.dutact.web.controller.checkin.student.StudentCheckInDetailDto;
import com.dutact.web.data.entity.EventCheckIn;
import com.dutact.web.data.repository.EventCheckInCodeRepository;
import com.dutact.web.data.repository.EventCheckInRepository;
import com.dutact.web.data.repository.StudentRepository;
import com.dutact.web.specs.EventCheckInSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventCheckInServiceImpl implements EventCheckInService {
    private final EventCheckInCodeRepository checkInCodeRepository;
    private final EventCheckInRepository checkInRepository;
    private final StudentRepository studentRepository;

    @Value("${event.checkin.radius_meters}")
    private double checkInRadiusMeters;

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

            var distance = GeoUtils.distance(
                    checkInCode.getLocation().getGeoPosition().getLat(),
                    checkInCode.getLocation().getGeoPosition().getLng(),
                    geoPosition.getLat(),
                    geoPosition.getLng()
            );
            var distanceInMeters = distance * 1000;

            if (distanceInMeters > checkInRadiusMeters) {
                throw new OutOfRangeException(String.format("Distance is %.2f meters, greater than %.2f meters",
                        distanceInMeters, checkInRadiusMeters));
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
