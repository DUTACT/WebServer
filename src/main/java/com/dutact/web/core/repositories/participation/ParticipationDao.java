package com.dutact.web.core.repositories.participation;

import java.util.List;

public interface ParticipationDao {
    void confirmParticipation(ConfirmParticipationCondition condition);

    void rejectParticipation(RejectParticipationCondition condition);

    List<Integer> getRegisteredStudentsIdsDoesNotHaveCertificate(Integer eventId);
}
