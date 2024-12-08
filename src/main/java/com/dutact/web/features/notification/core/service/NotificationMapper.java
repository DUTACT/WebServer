package com.dutact.web.features.notification.core.service;

import com.dutact.web.features.notification.core.data.Notification;
import com.dutact.web.features.notification.core.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {
    NotificationDto toDto(Notification notification);
}
