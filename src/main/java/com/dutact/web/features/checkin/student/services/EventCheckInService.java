package com.dutact.web.features.checkin.student.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.checkin.student.dtos.EventCheckInParams;
import com.dutact.web.features.checkin.student.dtos.EventCheckInResult;
import com.dutact.web.features.checkin.student.dtos.StudentCheckInDetailDto;
import com.dutact.web.features.checkin.student.services.exceptions.AlreadyCheckInException;
import com.dutact.web.features.checkin.student.services.exceptions.EarlyCheckInAttemptException;
import com.dutact.web.features.checkin.student.services.exceptions.LateCheckInAttemptException;
import com.dutact.web.features.checkin.student.services.exceptions.OutOfRangeException;

public interface EventCheckInService {
    EventCheckInResult checkIn(EventCheckInParams params)
            throws EarlyCheckInAttemptException,
            AlreadyCheckInException,
            LateCheckInAttemptException,
            OutOfRangeException,
            NotExistsException;

    StudentCheckInDetailDto getCheckInDetail(Integer eventId, String username) throws NotExistsException;
}
