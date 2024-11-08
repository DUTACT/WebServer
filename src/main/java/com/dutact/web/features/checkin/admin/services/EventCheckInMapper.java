package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.core.projections.CheckInPreview;
import com.dutact.web.features.checkin.admin.dtos.CheckInPreviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventCheckInMapper {
    CheckInPreviewDto toDto(CheckInPreview checkInPreview);
}
