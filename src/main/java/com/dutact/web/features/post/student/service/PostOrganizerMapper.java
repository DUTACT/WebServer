package com.dutact.web.features.post.student.service;

import com.dutact.web.core.entities.EventOrganizer;
import com.dutact.web.features.event.student.dtos.OrganizerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostOrganizerMapper {
    @Mapping(target = "avatarUrl", source = "avatar.fileUrl")
    OrganizerDto toDto(EventOrganizer organizer);
}
