package com.dutact.web.core.repositories.views;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.core.projections.CheckInPreview;

public interface CheckInViewsRepository {
    PageResponse<CheckInPreview> getCheckInPreviews(CheckInQueryParams queryParams);
}
