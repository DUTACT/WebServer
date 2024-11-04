package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.checkin.admin.dtos.CreateEventCheckInCodeDto;
import com.dutact.web.features.checkin.admin.dtos.EventCheckInCodeDto;

import java.util.Collection;

public interface EventCheckInCodeService {
    EventCheckInCodeDto createCheckInCode(CreateEventCheckInCodeDto createEventCheckinCodeDto)
            throws NotExistsException;

    EventCheckInCodeDto getCheckInCode(Integer id) throws NotExistsException;

    Collection<EventCheckInCodeDto> getCheckInCodes(Integer eventId) throws NotExistsException;

    void deleteCheckInCode(Integer id);
}
