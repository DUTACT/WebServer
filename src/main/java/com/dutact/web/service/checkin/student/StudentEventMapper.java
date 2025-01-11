package com.dutact.web.service.checkin.student;

import com.dutact.web.controller.checkin.student.StudentCheckInDetailDto;
import com.dutact.web.controller.checkin.student.StudentRegistrationDto;
import com.dutact.web.data.entity.EventCheckIn;
import com.dutact.web.data.entity.checkincode.EventCheckInCode;
import com.dutact.web.data.entity.eventregistration.EventRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentEventMapper {
    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "title", source = "event.name")
    @Mapping(target = "coverPhotoUrl", expression = "java(event.getEvent().getCoverPhotos().isEmpty() ? null : event.getEvent().getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "coverPhotoUrls", expression = "java(event.getEvent().getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "startAt", source = "event.startAt")
    @Mapping(target = "endAt", source = "event.endAt")
    StudentRegistrationDto toEventPreviewDto(EventRegistration event);

    StudentCheckInDetailDto.CheckInHistoryDto toCheckInHistoryDto(EventCheckIn checkIn);

    StudentCheckInDetailDto.CheckInCodeInfo toCheckInCodeInfo(EventCheckInCode checkInCode);
} 