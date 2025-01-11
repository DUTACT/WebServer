package com.dutact.web.service.post.student;

import com.dutact.web.dto.event.student.OrganizerDto;
import com.dutact.web.data.entity.EventOrganizer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostOrganizerMapper {
    @Mapping(target = "avatarUrl", source = "avatar.fileUrl")
    OrganizerDto toDto(EventOrganizer organizer);
}
