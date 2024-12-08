package com.dutact.web.features.notification.core.service;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.features.notification.core.dto.NotificationDto;

public interface NotificationService {
    PageResponse<NotificationDto> getNotifications(int page, int size);
}
