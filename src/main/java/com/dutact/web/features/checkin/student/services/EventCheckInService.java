package com.dutact.web.features.checkin.student.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.checkin.student.dtos.EventCheckInResult;
import com.dutact.web.features.checkin.student.services.exceptions.AlreadyCheckInException;
import com.dutact.web.features.checkin.student.services.exceptions.EarlyCheckInAttemptException;
import com.dutact.web.features.checkin.student.services.exceptions.LateCheckInAttemptException;

public interface EventCheckInService {
    EventCheckInResult checkIn(String code, Integer studentId)
            throws EarlyCheckInAttemptException,
            AlreadyCheckInException,
            LateCheckInAttemptException,
            NotExistsException;
}
