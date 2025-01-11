package com.dutact.web.common.notification.core.service;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.notification.core.dto.NotificationDto;
import com.dutact.web.common.notification.core.dto.NotificationQueryParams;

import java.util.List;

public interface NotificationService {
    PageResponse<NotificationDto> getNotifications(NotificationQueryParams queryParams);

    NotificationDto markAsRead(Integer notificationId) throws NotExistsException;

    void markAllAsRead(Integer accountId);

    void markAsRead(List<Integer> notificationIds);
}
