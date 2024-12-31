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
    @Mapping(target = "coverPhotoUrl", expression = "java(event.getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "coverPhotoUrls", expression = "java(event.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "organizer.avatarUrl", source = "organizer.avatar.fileUrl")
    @Mapping(target = "followerNumber", ignore = true)
    @Mapping(target = "registerNumber", ignore = true)
    EventDto toDto(Event event);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "totalCheckIn", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    EventDetailsDto toDetailsDto(EventRegistration eventRegistration);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "totalCheckIn", ignore = true)
    @Mapping(target = "certificateStatus", ignore = true)
    @Mapping(target = "checkIns", ignore = true)
    EventDetailsDto toDetailsDto(EventFollow eventFollow);
}
