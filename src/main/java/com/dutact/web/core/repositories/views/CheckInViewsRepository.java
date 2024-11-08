package com.dutact.web.core.repositories.views;

import com.dutact.web.core.projections.CheckInPreview;
import org.springframework.data.domain.Page;

public interface CheckInViewsRepository {
    Page<CheckInPreview> getCheckInPreviews(CheckInQueryParams queryParams);
}
