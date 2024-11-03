package com.dutact.web.features.participation.admin.services;

import com.dutact.web.core.entities.EventParticipationCode;
import com.dutact.web.features.participation.admin.dtos.CreateEventParticipationCodeDto;
import com.dutact.web.features.participation.admin.dtos.EventParticipationCodeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventParticipationCodeMapper {
    EventParticipationCode toEventParticipationCode(CreateEventParticipationCodeDto createEventParticipationCodeDto);

    @Mapping(target = "eventId", source = "event.id")
    EventParticipationCodeDto toDto(EventParticipationCode eventParticipationCode);
}
