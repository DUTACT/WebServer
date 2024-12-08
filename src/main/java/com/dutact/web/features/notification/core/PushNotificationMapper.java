package com.dutact.web.features.notification.core;

import com.dutact.web.features.notification.core.data.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PushNotificationMapper {
    @Mapping(target = "notificationId", source = "id")
    PushNotificationMessage toMessage(Notification notification);
}
