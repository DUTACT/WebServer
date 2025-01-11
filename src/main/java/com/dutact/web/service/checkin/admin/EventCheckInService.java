package com.dutact.web.service.checkin.admin;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.data.repository.views.CheckInQueryParams;
import com.dutact.web.dto.checkin.admin.CheckInDetailDto;
import com.dutact.web.dto.checkin.admin.CheckInPreviewDto;

public interface EventCheckInService {
    PageResponse<CheckInPreviewDto> getCheckedInParticipants(CheckInQueryParams eventId);

    CheckInDetailDto getCheckInDetail(Integer eventId, Integer studentId) throws NotExistsException;
}
