package com.dutact.web.features.event.admin.services.event;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.features.event.admin.dtos.event.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.event.EventUpdateDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {
    @Mapping(target = "coverPhotoUrl", source = "coverPhoto.fileUrl")
    @Mapping(target = "organizer.avatarUrl", source = "organizer.avatar.fileUrl")
    EventDto toEventDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhoto", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "eventRegistrations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    Event toEvent(EventCreateDto eventDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhoto", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "eventRegistrations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEvent(@MappingTarget Event eventDto, EventUpdateDto event);
}
