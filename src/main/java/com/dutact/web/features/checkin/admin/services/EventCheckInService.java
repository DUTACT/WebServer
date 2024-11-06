package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.features.checkin.admin.dtos.GetParticipationQueryParam;
import com.dutact.web.features.checkin.admin.dtos.ParticipationPreviewDto;

public interface EventCheckInService {
    PageResponse<ParticipationPreviewDto> getCheckedInParticipants(GetParticipationQueryParam eventId);
}
