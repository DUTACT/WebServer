package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.features.checkin.admin.dtos.participation.ConfirmParticipationCriterion;
import com.dutact.web.features.checkin.admin.dtos.participation.RejectParticipationCriterion;

public interface EventParticipationService {
    void confirmParticipation(ConfirmParticipationCriterion confirmCriterion);

    void rejectParticipation(RejectParticipationCriterion rejectCriterion);
}
