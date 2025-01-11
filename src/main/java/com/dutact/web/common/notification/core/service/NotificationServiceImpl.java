package com.dutact.web.common.notification.core.service;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.notification.core.data.NotificationRepository;
import com.dutact.web.common.notification.core.data.NotificationSpecs;
import com.dutact.web.common.notification.core.dto.NotificationDto;
import com.dutact.web.common.notification.core.dto.NotificationQueryParams;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public PageResponse<NotificationDto> getNotifications(NotificationQueryParams queryParams) {
        var specs = NotificationSpecs.hasAccountId(queryParams.getAccountId())
                .and(NotificationSpecs.notExpired());

        var paging = PageRequest.of(queryParams.getPage() - 1, queryParams.getSize(),
                Sort.by("createdAt").descending());

        var page = notificationRepository.findAll(specs, paging);

        return PageResponse.of(page, notificationMapper::toDto);
    }

    @Override
    public NotificationDto markAsRead(Integer notificationId) throws NotExistsException {
        var notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotExistsException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);

        return notificationMapper.toDto(notification);
    }

    @Override
    public void markAllAsRead(Integer accountId) {
        var notifications = notificationRepository.findAll(NotificationSpecs.hasAccountId(accountId)
                .and(NotificationSpecs.notExpired())
                .and(NotificationSpecs.notRead()));

        notifications.forEach(notification -> {
            notification.setRead(true);
        });

        notificationRepository.saveAll(notifications);
    }

    @Override
    public void markAsRead(List<Integer> notificationIds) {
        var notifications = notificationRepository.findAllById(notificationIds);

        notifications.forEach(notification -> {
            notification.setRead(true);
        });

        notificationRepository.saveAll(notifications);
    }
}
