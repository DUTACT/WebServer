package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.core.entities.checkincode.EventCheckInCode;
import com.dutact.web.features.checkin.admin.dtos.CreateEventCheckInCodeDto;
import com.dutact.web.features.checkin.admin.dtos.EventCheckInCodeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventCheckInCodeMapper {
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "id", ignore = true)
    EventCheckInCode toCheckInCode(CreateEventCheckInCodeDto createEventCheckInCodeDto);

    @Mapping(target = "eventId", source = "event.id")
    EventCheckInCodeDto toDto(EventCheckInCode eventCheckInCode);
}
