package com.dutact.web.service.event.student;

import com.dutact.web.data.entity.EventFollow;
import com.dutact.web.dto.event.student.EventFollowDto;
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
    EventFollowDto toDto(EventFollow eventFollow);
}
