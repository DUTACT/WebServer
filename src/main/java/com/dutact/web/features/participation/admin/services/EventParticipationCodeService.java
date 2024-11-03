package com.dutact.web.features.participation.admin.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.participation.admin.dtos.CreateEventParticipationCodeDto;
import com.dutact.web.features.participation.admin.dtos.EventParticipationCodeDto;

import java.util.Collection;

public interface EventParticipationCodeService {
    EventParticipationCodeDto createEventParticipationCode(
            Integer eventId,
            CreateEventParticipationCodeDto createEventParticipationCodeDto)
            throws NotExistsException;

    EventParticipationCodeDto getEventParticipationCode(Integer id) throws NotExistsException;

    Collection<EventParticipationCodeDto> getEventParticipationCodes(Integer eventId) throws NotExistsException;

    void deleteEventParticipationCode(Integer id);
}
