package com.dutact.web.common.notification.core.service;

import com.dutact.web.common.notification.core.data.Notification;
import com.dutact.web.common.notification.core.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {
    NotificationDto toDto(Notification notification);
}
