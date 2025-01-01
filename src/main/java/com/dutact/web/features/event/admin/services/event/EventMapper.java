package com.dutact.web.features.event.admin.services.event;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.features.event.admin.dtos.event.*;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {
    @Mapping(target = "coverPhotoUrl", expression = "java(event.getCoverPhotos().isEmpty() ? null : event.getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "coverPhotoUrls", expression = "java(event.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "organizer.avatarUrl", source = "organizer.avatar.fileUrl")
    EventDto toEventDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "eventRegistrations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    Event toEvent(EventCreateDtoV2 eventDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "eventRegistrations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    Event toEvent(EventCreateDtoV1 eventDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "eventRegistrations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEvent(@MappingTarget Event eventDto, EventUpdateDtoV2 event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "eventRegistrations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEvent(@MappingTarget Event eventDto, EventUpdateDtoV1 event);
}
