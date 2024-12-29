package com.dutact.web.features.notification.core.service;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.notification.core.dto.NotificationDto;
import com.dutact.web.features.notification.core.dto.NotificationQueryParams;

public interface NotificationService {
    PageResponse<NotificationDto> getNotifications(NotificationQueryParams queryParams);

    NotificationDto markAsRead(Integer notificationId) throws NotExistsException;
}
