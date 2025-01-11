package com.dutact.web.service.profile.student;

import com.dutact.web.dto.profile.student.StudentProfileDto;
import com.dutact.web.dto.profile.student.StudentProfileUpdateDto;
import com.dutact.web.data.entity.Student;
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
    @Mapping(target = "avatar", ignore = true)
    void updateProfile(@MappingTarget Student student, StudentProfileUpdateDto studentProfileUpdateDto);
}
