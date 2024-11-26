package com.dutact.web.features.notification.core;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PushNotificationMapper {
    PushNotificationMessage toMessage(Notification notification);
}
