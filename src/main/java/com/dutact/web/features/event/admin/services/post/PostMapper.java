package com.dutact.web.features.event.admin.services.post;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.post.Post;
import com.dutact.web.features.event.admin.dtos.event.EventUpdateDto;
import com.dutact.web.features.event.admin.dtos.post.PostCreateDto;
import com.dutact.web.features.event.admin.dtos.post.PostDto;
import com.dutact.web.features.event.admin.dtos.post.PostUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "coverPhotoUrl", source = "coverPhoto.fileUrl")
    PostDto toPostDto(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhoto", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "event.id", source = "eventId")
    Post toPost(PostCreateDto eventDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhoto", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "event.id", source = "eventId")
    void updatePost(@MappingTarget Post post, PostUpdateDto postUpdateDto);
}
