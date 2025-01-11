package com.dutact.web.service.post.student;

import com.dutact.web.dto.post.student.PostDto;
import com.dutact.web.data.entity.post.Post;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@AnnotateWith(
        value = Component.class,
        elements = @AnnotateWith.Element(strings = "studentPostMapper"))
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = PostOrganizerMapper.class)
public interface PostMapper {
    @Mapping(target = "coverPhotoUrl", expression = "java(post.getCoverPhotos().isEmpty() ? null : post.getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "coverPhotoUrls", expression = "java(post.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    @Mapping(target = "likedAt", ignore = true)
    @Mapping(target = "likeNumber", ignore = true)
    @Mapping(target = "organizer", source = "event.organizer")
    PostDto toDto(Post post);
}
