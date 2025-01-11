package com.dutact.web.service.event.admin;

import com.dutact.web.dto.event.admin.EventChangeDto;
import com.dutact.web.data.entity.eventchange.EventChange;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventChangeMapper {
    EventChangeDto toDto(EventChange eventChange);
}
