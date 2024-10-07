package com.dutact.web.features.event.admin.services;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.features.event.admin.dtos.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.EventDto;
import com.dutact.web.features.event.admin.dtos.EventUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {
    @Mapping(target = "organizerId", source = "organizer.id")
    @Mapping(target = "coverPhotoUrl", source = "coverPhoto.fileUrl")
    EventDto toEventDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhoto", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "organizer.id", source = "organizerId")
    Event toEvent(EventCreateDto eventDto);

    @Mapping(target = "coverPhotoUrl", source = "coverPhoto.fileUrl")
    void updateEvent(@MappingTarget EventUpdateDto eventDto, Event event);
}
