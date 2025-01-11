package com.dutact.web.service.checkin.admin;

import com.dutact.web.controller.checkin.student.StudentCheckInDetailDto;
import com.dutact.web.data.entity.EventCheckIn;
import com.dutact.web.data.entity.checkincode.EventCheckInCode;
import com.dutact.web.data.projection.CheckInPreview;
import com.dutact.web.dto.checkin.admin.CheckInCodeInfo;
import com.dutact.web.dto.checkin.admin.CheckInHistoryDto;
import com.dutact.web.dto.checkin.admin.CheckInPreviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

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

    StudentCheckInDetailDto.CheckInHistoryDto toDto(EventCheckIn eventCheckIn);
}
