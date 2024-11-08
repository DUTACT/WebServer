package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.core.repositories.views.CheckInQueryParams;
import com.dutact.web.features.checkin.admin.dtos.CheckInPreviewDto;

public interface EventCheckInService {
    PageResponse<CheckInPreviewDto> getCheckedInParticipants(CheckInQueryParams eventId);
}
