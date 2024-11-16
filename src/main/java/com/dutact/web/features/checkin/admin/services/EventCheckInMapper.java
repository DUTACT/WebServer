package com.dutact.web.features.checkin.admin.services;

import com.dutact.web.core.entities.EventCheckIn;
import com.dutact.web.core.entities.EventCheckInCode;
import com.dutact.web.core.projections.CheckInPreview;
import com.dutact.web.features.checkin.admin.dtos.CheckInCodeInfo;
import com.dutact.web.features.checkin.admin.dtos.CheckInHistoryDto;
import com.dutact.web.features.checkin.admin.dtos.CheckInPreviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mapping;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventCheckInMapper {
    CheckInPreviewDto toDto(CheckInPreview checkInPreview);
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "checkInTime", source = "checkInTime")
    @Mapping(target = "checkInCode", source = "checkInCode")
    CheckInHistoryDto toCheckInHistoryDto(EventCheckIn checkIn);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "startAt", source = "startAt")
    @Mapping(target = "endAt", source = "endAt")
    CheckInCodeInfo toCheckInCodeInfo(EventCheckInCode checkInCode);
}
