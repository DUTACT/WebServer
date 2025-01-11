package com.dutact.web.service.liker;

import com.dutact.web.dto.liker.StudentBasicInfoDto;
import com.dutact.web.data.entity.Student;
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