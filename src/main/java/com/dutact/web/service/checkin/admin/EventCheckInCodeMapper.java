package com.dutact.web.service.checkin.admin;

import com.dutact.web.dto.checkin.admin.CreateEventCheckInCodeDto;
import com.dutact.web.dto.checkin.admin.EventCheckInCodeDto;
import com.dutact.web.data.entity.checkincode.EventCheckInCode;
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
