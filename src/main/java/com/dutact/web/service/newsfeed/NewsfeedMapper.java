package com.dutact.web.service.newsfeed;

import com.dutact.web.dto.newsfeed.NewsfeedItemDto;
import com.dutact.web.data.entity.feedback.Feedback;
import com.dutact.web.data.entity.post.Post;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentNewsfeedMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NewsfeedMapper {
    @Mapping(target = "coverPhotoUrl", expression = "java(post.getCoverPhotos().isEmpty() ? null : post.getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "coverPhotoUrls", expression = "java(post.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "organizer.name", source = "event.organizer.name")
    @Mapping(target = "organizer.avatarUrl", source = "event.organizer.avatar.fileUrl")
    @Mapping(target = "event", source = "event")
    @Mapping(target = "event.coverPhotoUrl", expression = "java(event.getCoverPhotos().isEmpty() ? null : event.getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "event.coverPhotoUrls", expression = "java(event.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "likedAt", ignore = true)
    @Mapping(target = "likeNumber", ignore = true)
    NewsfeedItemDto.NewsfeedPostDto toPostDto(Post post);

    @Mapping(target = "coverPhotoUrl", expression = "java(feedback.getCoverPhotos().isEmpty() ? null : feedback.getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "coverPhotoUrls", expression = "java(feedback.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "student", source = "student")
    @Mapping(target = "student.avatarUrl", source = "student.avatar.fileUrl")
    @Mapping(target = "student.name", source = "student.fullName")
    @Mapping(target = "event", source = "event")
    @Mapping(target = "event.coverPhotoUrl", expression = "java(event.getCoverPhotos().isEmpty() ? null : event.getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "event.coverPhotoUrls", expression = "java(event.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "likedAt", ignore = true)
    @Mapping(target = "likeNumber", ignore = true)
    NewsfeedItemDto.NewsfeedFeedbackDto toFeedbackDto(Feedback feedback);
}
