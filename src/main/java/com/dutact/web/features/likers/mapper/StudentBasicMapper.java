package com.dutact.web.features.likers.mapper;

import com.dutact.web.core.entities.Student;
import com.dutact.web.features.likers.dto.StudentBasicInfoDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentBasicMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentBasicMapper {
    
    @Mapping(target = "email", source = "username")
    @Mapping(target = "avatarUrl", source = "avatar.fileUrl")
    StudentBasicInfoDto toBasicInfoDto(Student student);
}