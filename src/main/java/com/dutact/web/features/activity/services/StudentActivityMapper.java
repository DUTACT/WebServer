package com.dutact.web.features.activity.services;

import com.dutact.web.core.entities.StudentActivity;
import com.dutact.web.features.activity.dto.StudentActivityDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentActivityMapper {
    
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "eventName", source = "event.name")
    StudentActivityDto toDto(StudentActivity activity);
} 