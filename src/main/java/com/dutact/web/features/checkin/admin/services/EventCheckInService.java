package com.dutact.web.features.checkin.admin.services;

import java.util.List;

public interface EventCheckInService {
    List<String> getCheckedInParticipants(Integer eventId);
}
