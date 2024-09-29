package com.dutact.web.features.event.admin.services;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.features.event.admin.dtos.EventCreateUpdateDto;
import com.dutact.web.features.event.admin.dtos.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {
    EventDto toEventDto(Event event);

    Event toEvent(EventCreateUpdateDto eventDto);

    void updateEvent(@MappingTarget EventCreateUpdateDto eventDto, Event event);
}
