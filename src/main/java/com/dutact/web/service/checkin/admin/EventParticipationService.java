package com.dutact.web.service.checkin.admin;

import com.dutact.web.dto.checkin.admin.participation.ConfirmParticipationCriterion;
import com.dutact.web.dto.checkin.admin.participation.RejectParticipationCriterion;

public interface EventParticipationService {
    void confirmParticipation(Integer eventId, ConfirmParticipationCriterion confirmCriterion);

    void rejectParticipation(Integer eventId, RejectParticipationCriterion rejectCriterion);
}
