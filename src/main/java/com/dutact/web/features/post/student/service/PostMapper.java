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
    PostDto toDto(Post post);
}
