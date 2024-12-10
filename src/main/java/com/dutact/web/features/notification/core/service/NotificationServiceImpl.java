package com.dutact.web.features.notification.core.service;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.features.notification.core.data.NotificationRepository;
import com.dutact.web.features.notification.core.data.NotificationSpecs;
import com.dutact.web.features.notification.core.dto.NotificationDto;
import com.dutact.web.features.notification.core.dto.NotificationQueryParams;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
}
