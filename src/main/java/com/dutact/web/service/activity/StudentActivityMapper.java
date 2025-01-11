package com.dutact.web.service.activity;

import com.dutact.web.dto.activity.StudentActivityDto;
import com.dutact.web.data.entity.StudentActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentActivityMapper {

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "eventName", source = "event.name")
    StudentActivityDto toDto(StudentActivity activity);
} 