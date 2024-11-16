package com.dutact.web.features.event.admin.services.event;

import com.dutact.web.core.entities.eventchange.EventChange;
import com.dutact.web.features.event.admin.dtos.event.EventChangeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventChangeMapper {
    EventChangeDto toDto(EventChange eventChange);
}
