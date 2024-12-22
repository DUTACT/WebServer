package com.dutact.web.features.analytics.admin.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationCountByDateDto;
import com.dutact.web.features.analytics.admin.dtos.organizer.OrganizerOverallStatsDto;

import java.time.LocalDate;
import java.util.List;

public interface EventAnalyticsService {
    List<EventRegistrationCountByDateDto> getEventParticipations(Integer eventId, LocalDate start, LocalDate end) throws NotExistsException;
    List<EventRegistrationCountByDateDto> getEventRegistrations(Integer eventId, LocalDate start, LocalDate end) throws NotExistsException;
    OrganizerOverallStatsDto getOrganizerOverallStats(Integer organizerId) throws NotExistsException;
    OrganizerOverallStatsDto getOrganizerOverallStatsByUsername(String username) throws NotExistsException;
}