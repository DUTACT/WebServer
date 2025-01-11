package com.dutact.web.service.checkin.student;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.controller.checkin.student.EventCheckInParams;
import com.dutact.web.controller.checkin.student.EventCheckInResult;
import com.dutact.web.controller.checkin.student.StudentCheckInDetailDto;

public interface EventCheckInService {
    EventCheckInResult checkIn(EventCheckInParams params)
            throws EarlyCheckInAttemptException,
            AlreadyCheckInException,
            LateCheckInAttemptException,
            OutOfRangeException,
            NotExistsException;

    StudentCheckInDetailDto getCheckInDetail(Integer eventId, String username) throws NotExistsException;
}
