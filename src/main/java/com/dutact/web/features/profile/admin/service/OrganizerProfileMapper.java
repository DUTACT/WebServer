package com.dutact.web.features.profile.admin.service;

import com.dutact.web.core.entities.EventOrganizer;
import com.dutact.web.features.profile.admin.dtos.OrganizerProfileDto;
import com.dutact.web.features.profile.admin.dtos.OrganizerProfileUpdateDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "organizerProfileMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrganizerProfileMapper {
    @Mapping(target = "avatarUrl", source = "avatar.fileUrl")
    OrganizerProfileDto toProfileDto(EventOrganizer eventOrganizer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "events", ignore = true)
    void updateProfile(@MappingTarget EventOrganizer eventOrganizer, OrganizerProfileUpdateDto organizerProfileUpdateDto);
}
