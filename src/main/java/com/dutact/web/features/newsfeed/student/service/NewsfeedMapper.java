package com.dutact.web.features.newsfeed.student.service;

import com.dutact.web.core.entities.feedback.Feedback;
import com.dutact.web.core.entities.post.Post;
import com.dutact.web.features.feedback.student.dtos.CreateFeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackDto;
import com.dutact.web.features.feedback.student.dtos.UpdateFeedbackDto;
import com.dutact.web.features.newsfeed.student.dtos.NewsfeedElementDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentNewsfeedMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NewsfeedMapper {
    @Mapping(target = "coverPhotoUrl", source = "coverPhoto.fileUrl")
    @Mapping(target = "organizer.name", source = "event.organizer.name")
    @Mapping(target = "organizer.avatarUrl", source = "event.organizer.avatarUrl")
    @Mapping(target = "event", source = "event")
    @Mapping(target = "event.coverPhotoUrl", source = "event.coverPhoto.fileUrl")
    NewsfeedElementDto.NewsfeedPostDto toPostDto(Post post);

    @Mapping(target = "coverPhotoUrl", source = "coverPhoto.fileUrl")
    @Mapping(target = "student", source = "student")
    @Mapping(target = "student.avatarUrl", source = "student.avatarUrl")
    @Mapping(target = "student.name", source = "student.fullName")
    @Mapping(target = "event", source = "event")
    @Mapping(target = "event.coverPhotoUrl", source = "event.coverPhoto.fileUrl")
    NewsfeedElementDto.NewsfeedFeedbackDto toFeedbackDto(Feedback feedback);
}
