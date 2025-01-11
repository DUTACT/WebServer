package com.dutact.web.data.repository.views;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.data.projection.CheckInPreview;

public interface CheckInViewsRepository {
    PageResponse<CheckInPreview> getCheckInPreviews(CheckInQueryParams queryParams);
}
