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

        List<RegistrationCountByDate> registrations = eventRegistrationRepository
            .countRegistrationByDateBetween(eventId, startDate.atTime(0,0), endDate.atTime(0,0));
            

        List<EventRegistrationCountByDateDto> res = new java.util.ArrayList<>(registrations.stream().map(this::toDto).toList());
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            LocalDate finalDate = date;
            if (registrations.stream().noneMatch(r -> r.getDate().toLocalDate().equals(finalDate))) {
                res.add(new EventRegistrationCountByDateDto(date, 0));
            }
        }
        res.sort(Comparator.comparing(EventRegistrationCountByDateDto::getDate));
        return res;
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

        List<EventRegistrationCountByDateDto> res = new java.util.ArrayList<>(participations.stream().map(this::toDto).toList());
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            LocalDate finalDate = date;
            if (participations.stream().noneMatch(r -> r.getDate().toLocalDate().equals(finalDate))) {
                res.add(new EventRegistrationCountByDateDto(date, 0));
            }
        }
        res.sort(Comparator.comparing(EventRegistrationCountByDateDto::getDate));
        return res;
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
}
