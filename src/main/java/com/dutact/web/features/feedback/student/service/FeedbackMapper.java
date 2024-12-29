package com.dutact.web.features.feedback.student.service;

import com.dutact.web.core.entities.feedback.Feedback;
import com.dutact.web.features.feedback.student.dtos.CreateFeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackDto;
import com.dutact.web.features.feedback.student.dtos.UpdateFeedbackDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentFeedbackMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FeedbackMapper {
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    @Mapping(target = "id", ignore = true)
    Feedback toFeedback(CreateFeedbackDto createFeedbackDto);

    @Mapping(target = "coverPhotoUrls", expression = "java(feedback.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "event.coverPhotoUrls", expression = "java(event.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "student.avatarUrl", source = "student.avatar.fileUrl")
    @Mapping(target = "likedAt", ignore = true)
    @Mapping(target = "likeNumber", ignore = true)
    FeedbackDto toDto(Feedback feedback);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    void updateFeedback(@MappingTarget Feedback feedback,
                        UpdateFeedbackDto updateFeedbackDto);
}
