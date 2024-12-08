package com.dutact.web.features.notification.core.service;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.features.notification.core.data.NotificationRepository;
import com.dutact.web.features.notification.core.dto.NotificationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public PageResponse<NotificationDto> getNotifications(int page, int size) {
        return null;
    }
}
