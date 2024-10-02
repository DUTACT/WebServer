package com.dutact.web.features.event.student.services;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.features.event.student.dtos.EventDto;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentEventMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {
    EventDto toDto(Event event);
}
