package com.dutact.web.features.profile.student.service;

import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.features.event.admin.dtos.event.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.event.EventUpdateDto;
import com.dutact.web.features.profile.student.dtos.StudentProfileDto;
import com.dutact.web.features.profile.student.dtos.StudentProfileUpdateDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentProfileMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentProfileMapper {
    @Mapping(target = "studentEmail", source = "username")
    @Mapping(target = "avatarUrl", source = "avatar.fileUrl")
    StudentProfileDto toProfileDto(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "eventRegistrations", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    void updateProfile(@MappingTarget Student student, StudentProfileUpdateDto studentProfileUpdateDto);
}
