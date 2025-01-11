package com.dutact.web.service.event.student;

import com.dutact.web.data.entity.eventregistration.EventRegistration;
import com.dutact.web.dto.event.student.EventRegisteredDto;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentEventRegistrationMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventRegistrationMapper {
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "registeredAt", source = "registeredAt")
    EventRegisteredDto toDto(EventRegistration eventRegistration);
}
