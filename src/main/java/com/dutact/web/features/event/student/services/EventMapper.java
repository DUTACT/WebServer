package com.dutact.web.features.event.student.services;

import com.dutact.web.core.entities.EventFollow;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.eventregistration.EventRegistration;
import com.dutact.web.features.event.student.dtos.EventDetailsDto;
import com.dutact.web.features.event.student.dtos.EventDto;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentEventMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "followedAt", ignore = true)
    @Mapping(target = "coverPhotoUrl", source = "coverPhoto.fileUrl")
    @Mapping(target = "organizer.avatarUrl", source = "organizer.avatar.fileUrl")
    EventDto toDto(Event event);

    @Mapping(target = "studentId", source = "student.id")
    EventDetailsDto toDetailsDto(EventRegistration eventRegistration);

    @Mapping(target = "studentId", source = "student.id")
    EventDetailsDto toDetailsDto(EventFollow eventFollow);
}
