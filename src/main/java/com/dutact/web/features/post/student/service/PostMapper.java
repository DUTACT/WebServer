package com.dutact.web.features.post.student.service;

import com.dutact.web.core.entities.post.Post;
import com.dutact.web.features.post.student.dtos.PostDto;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentPostMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    @Mapping(target = "coverPhotoUrl", source = "coverPhoto.fileUrl")
    @Mapping(target = "likedAt", ignore = true)
    @Mapping(target = "likeNumber", ignore = true)
    @Mapping(target = "organizer", source = "event.organizer")
    @Mapping(target = "organizer.avatarUrl", source = "event.organizer.avatar.fileUrl")
    PostDto toDto(Post post);
}
