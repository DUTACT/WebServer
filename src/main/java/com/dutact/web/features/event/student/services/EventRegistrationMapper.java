package com.dutact.web.features.event.student.services;

import com.dutact.web.core.entities.eventregistration.EventRegistration;
import com.dutact.web.features.event.student.dtos.EventRegisteredDetailsDto;
import com.dutact.web.features.event.student.dtos.EventRegisteredDto;
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

    @Mapping(target = "studentId", source = "student.id")
    EventRegisteredDetailsDto toDetailsDto(EventRegistration eventRegistration);
}
