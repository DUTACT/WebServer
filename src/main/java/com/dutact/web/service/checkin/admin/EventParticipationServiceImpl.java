package com.dutact.web.service.checkin.admin;

import com.dutact.web.data.entity.event.CannotChangeStatusException;
import com.dutact.web.data.entity.eventregistration.participationcert.ParticipationCertificateStatus;
import com.dutact.web.data.repository.EventRegistrationRepository;
import com.dutact.web.dto.checkin.admin.participation.ConfirmParticipationCriterion;
import com.dutact.web.dto.checkin.admin.participation.RejectParticipationCriterion;
import com.dutact.web.specs.EventRegistrationSpecs;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class EventParticipationServiceImpl implements EventParticipationService {
    private final EventRegistrationRepository registrationRepository;

    public EventParticipationServiceImpl(EventRegistrationRepository registrationRepository) {

        this.registrationRepository = registrationRepository;
    }

    @Override
    @Transactional
    public void confirmParticipation(Integer eventId, ConfirmParticipationCriterion confirmCriterion) {
        var specs = EventRegistrationSpecs.hasEventId(eventId);
        specs = specs.and(EventRegistrationSpecs
                .hasCertificateStatus(ParticipationCertificateStatus.Pending.TYPE_NAME));

        if (confirmCriterion instanceof ConfirmParticipationCriterion.WithStudentsIds criterion) {
            specs = specs.and(EventRegistrationSpecs
                    .hasStudentIds(criterion.getStudentsIds()));
        } else if (confirmCriterion instanceof ConfirmParticipationCriterion.CheckedInAtLeast criterion) {
            specs = specs.and(EventRegistrationSpecs
                    .checkedInAtLeast(criterion.getCount()));
        } else if (!(confirmCriterion instanceof ConfirmParticipationCriterion.All)) {
            log.error("Unknown confirm criterion type: {}", confirmCriterion.getClass());
            return;
        }

        var registrations = registrationRepository.findAll(specs);

        if (registrations.isEmpty()) {
            log.info("No participations found for the given criterion");
            return;
        }

        registrations.forEach(registration -> {
            try {
                registration.setCertificateStatus(new ParticipationCertificateStatus.Confirmed());
            } catch (CannotChangeStatusException e) {
                log.error("Cannot change status of registration with id: {} to confirmed",
                        registration.getId());
            }
        });

        registrationRepository.saveAll(registrations);
    }

    @Override
    public void rejectParticipation(Integer eventId, RejectParticipationCriterion rejectCriterion) {
        var specs = EventRegistrationSpecs.hasEventId(eventId);
        specs = specs.and(EventRegistrationSpecs
                .hasCertificateStatus(ParticipationCertificateStatus.Pending.TYPE_NAME));

        if (rejectCriterion instanceof RejectParticipationCriterion.WithStudentsIds criterion) {
            specs = specs.and(EventRegistrationSpecs
                    .hasStudentIds(criterion.getStudentsIds()));
        } else if (!(rejectCriterion instanceof RejectParticipationCriterion.All)) {
            log.error("Unknown reject criterion type: {}", rejectCriterion.getClass());
            return;
        }

        var registrations = registrationRepository.findAll(specs);

        if (registrations.isEmpty()) {
            log.info("No participations found for the given criterion");
            return;
        }

        registrations.forEach(registration -> {
            try {
                registration.setCertificateStatus(new ParticipationCertificateStatus.Rejected(rejectCriterion.getReason()));
            } catch (CannotChangeStatusException e) {
                log.error("Cannot change status of registration with id: {} to rejected",
                        registration.getId());
            }
        });

        registrationRepository.saveAll(registrations);
    }
}
