package com.dutact.web.features.checkin.student.services;

import com.dutact.web.core.entities.EventCheckIn;
import com.dutact.web.core.entities.checkincode.EventCheckInCode;
import com.dutact.web.core.entities.eventregistration.EventRegistration;
import com.dutact.web.features.checkin.student.dtos.StudentCheckInDetailDto;
import com.dutact.web.features.checkin.student.dtos.StudentRegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentEventMapper {
    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "title", source = "event.name")
    @Mapping(target = "coverPhotoUrls", expression = "java(event.getEvent().getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "startAt", source = "event.startAt")
    @Mapping(target = "endAt", source = "event.endAt")
    StudentRegistrationDto toEventPreviewDto(EventRegistration event);

    StudentCheckInDetailDto.CheckInHistoryDto toCheckInHistoryDto(EventCheckIn checkIn);

    StudentCheckInDetailDto.CheckInCodeInfo toCheckInCodeInfo(EventCheckInCode checkInCode);
} 