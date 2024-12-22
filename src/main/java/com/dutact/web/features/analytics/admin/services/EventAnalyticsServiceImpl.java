package com.dutact.web.features.analytics.admin.services;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.eventregistration.participationcert.ParticipationCertificateStatus;
import com.dutact.web.core.projections.RegistrationCountByDate;
import com.dutact.web.core.repositories.EventRegistrationRepository;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.core.repositories.EventFollowRepository;
import com.dutact.web.core.repositories.PostLikeRepository;
import com.dutact.web.core.repositories.FeedbackRepository;
import com.dutact.web.core.repositories.FeedbackLikeRepository;
import com.dutact.web.core.repositories.OrganizerRepository;
import com.dutact.web.features.analytics.admin.dtos.registration.EventRegistrationCountByDateDto;
import com.dutact.web.features.analytics.admin.dtos.organizer.OrganizerOverallStatsDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventAnalyticsServiceImpl implements EventAnalyticsService {
    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventFollowRepository eventFollowRepository;
    private final PostLikeRepository postLikeRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackLikeRepository feedbackLikeRepository;
    private final OrganizerRepository organizerRepository;

    public EventAnalyticsServiceImpl(
            EventRepository eventRepository,
            EventRegistrationRepository eventRegistrationRepository,
            EventFollowRepository eventFollowRepository,
            PostLikeRepository postLikeRepository,
            FeedbackRepository feedbackRepository,
            FeedbackLikeRepository feedbackLikeRepository,
            OrganizerRepository organizerRepository) {
        this.eventRepository = eventRepository;
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventFollowRepository = eventFollowRepository;
        this.postLikeRepository = postLikeRepository;
        this.feedbackRepository = feedbackRepository;
        this.feedbackLikeRepository = feedbackLikeRepository;
        this.organizerRepository = organizerRepository;
    }

    @Override
    public List<EventRegistrationCountByDateDto> getEventRegistrations(
            Integer eventId,
            LocalDate startDate,
            LocalDate endDate) throws NotExistsException {
        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException("Event not found.");
        }

        return eventRegistrationRepository
            .countRegistrationByDateBetween(eventId, startDate.atTime(0,0), endDate.atTime(0,0)).stream().map(this::toDto).toList();
    }

    @Override
    public List<EventRegistrationCountByDateDto> getEventRegistrations(
            Integer eventId) throws NotExistsException {
        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException("Event not found.");
        }

        List<RegistrationCountByDate> registrations = eventRegistrationRepository.countRegistrationByDate(eventId);

        return registrations.stream().map(this::toDto).toList();
    }
    @Override
    public List<EventRegistrationCountByDateDto> getEventParticipations(
            Integer eventId,
            LocalDate startDate,
            LocalDate endDate) throws NotExistsException {
        if (!eventRepository.existsById(eventId)) {
            throw new NotExistsException("Event not found.");
        }

        List<RegistrationCountByDate> participations = eventRegistrationRepository
            .countRegistrationByDateBetween(eventId, startDate.atTime(0,0), endDate.atTime(0,0), ParticipationCertificateStatus.Confirmed.TYPE_NAME);

        return new java.util.ArrayList<>(participations.stream().map(this::toDto).toList());
    }

    private EventRegistrationCountByDateDto toDto(RegistrationCountByDate registration) {
        var dto = new EventRegistrationCountByDateDto();
        dto.setDate(registration.getDate().toLocalDate());
        dto.setCount(registration.getCount().intValue());
        return dto;
    }

    @Override
    public OrganizerOverallStatsDto getOrganizerOverallStats(Integer organizerId) throws NotExistsException {
        if (!organizerRepository.existsById(organizerId)) {
            throw new NotExistsException("Organizer not found.");
        }

        return new OrganizerOverallStatsDto(
            eventRepository.countByOrganizerId(organizerId),
            eventFollowRepository.countByOrganizerId(organizerId),
            postLikeRepository.countByOrganizerId(organizerId),
            feedbackRepository.countByOrganizerId(organizerId),
            feedbackLikeRepository.countByOrganizerId(organizerId)
        );
    }

    @Override
    public OrganizerOverallStatsDto getOrganizerOverallStatsByUsername(String username) throws NotExistsException {
        var organizer = organizerRepository.findByUsername(username)
            .orElseThrow(() -> new NotExistsException("Organizer not found."));
            
        return getOrganizerOverallStats(organizer.getId());
    }

    @Override
    public List<EventRegistrationCountByDateDto> getRegistrationStats(
            Integer organizerId,
            LocalDate startDate, 
            LocalDate endDate) {

        return eventRegistrationRepository
                .countOrganizerRegistrationsByDateBetween(
                    organizerId,
                    startDate.atStartOfDay(),
                    endDate.plusDays(1).atStartOfDay()
                )
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventRegistrationCountByDateDto> getParticipationStats(
            Integer organizerId, 
            LocalDate startDate, 
            LocalDate endDate) {
        
        return eventRegistrationRepository
                .countOrganizerParticipationsByDateBetween(
                    organizerId,
                    startDate.atStartOfDay(),
                    endDate.plusDays(1).atStartOfDay(),
                    ParticipationCertificateStatus.Confirmed.TYPE_NAME
                )
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
