package com.dutact.web.service.feedback.student;

import com.dutact.web.dto.feedback.student.*;
import com.dutact.web.data.entity.feedback.Feedback;
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
    Feedback toFeedback(CreateFeedbackDtoV2 createFeedbackDtoV2);

    @Mapping(target = "event", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    @Mapping(target = "id", ignore = true)
    Feedback toFeedback(CreateFeedbackDtoV1 createFeedbackDtoV1);

    @Mapping(target = "coverPhotoUrls", expression = "java(feedback.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "coverPhotoUrl", expression = "java(feedback.getCoverPhotos().isEmpty() ? null : feedback.getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "event.coverPhotoUrl", expression = "java(event.getCoverPhotos().isEmpty() ? null : event.getCoverPhotos().get(0).getFileUrl())")
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
                        UpdateFeedbackDtoV2 updateFeedbackDtoV2);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    void updateFeedback(@MappingTarget Feedback feedback,
                        UpdateFeedbackDtoV1 updateFeedbackDtoV1);
}
