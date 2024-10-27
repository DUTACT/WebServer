package com.dutact.web.features.event.student.services;

import com.dutact.web.core.entities.EventFollow;
import com.dutact.web.features.event.student.dtos.EventFollowDto;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentEventFollowMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventFollowMapper {
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "followedAt", source = "followedAt")
    EventFollowDto toDto(EventFollow eventFollow);
}
