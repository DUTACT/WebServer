package com.dutact.web.service.feedback.admin;

import com.dutact.web.dto.feedback.admin.FeedbackDto;
import com.dutact.web.data.entity.feedback.Feedback;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentFeedbackManagementMapper"))
public interface FeedbackMapper {
    @Mapping(target = "studentName", source = "student.fullName")
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentAvatarUrl", source = "student.avatar.fileUrl")
    @Mapping(target = "coverPhotoUrl", expression = "java(feedback.getCoverPhotos().isEmpty() ? null : feedback.getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "coverPhotoUrls", expression = "java(feedback.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    FeedbackDto toDto(Feedback feedback);
}
