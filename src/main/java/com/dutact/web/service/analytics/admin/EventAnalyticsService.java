package com.dutact.web.service.analytics.admin;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.analytics.organizer.OrganizerOverallStatsDto;
import com.dutact.web.dto.analytics.registration.EventRegistrationCountByDateDto;

import java.time.LocalDate;
import java.util.List;

public interface EventAnalyticsService {
    List<EventRegistrationCountByDateDto> getEventParticipations(Integer eventId, LocalDate start, LocalDate end) throws NotExistsException;

    List<EventRegistrationCountByDateDto> getEventRegistrations(Integer eventId) throws NotExistsException;

    List<EventRegistrationCountByDateDto> getEventRegistrations(Integer eventId, LocalDate start, LocalDate end) throws NotExistsException;

    OrganizerOverallStatsDto getOrganizerOverallStats(Integer organizerId) throws NotExistsException;

    OrganizerOverallStatsDto getOrganizerOverallStatsByUsername(String username) throws NotExistsException;

    List<EventRegistrationCountByDateDto> getRegistrationStats(
            Integer organizerId,
            LocalDate startDate,
            LocalDate endDate);

    List<EventRegistrationCountByDateDto> getParticipationStats(
            Integer organizerId,
            LocalDate startDate,
            LocalDate endDate);
}