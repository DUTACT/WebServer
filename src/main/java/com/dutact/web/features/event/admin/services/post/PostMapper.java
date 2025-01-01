package com.dutact.web.features.event.admin.services.post;

import com.dutact.web.core.entities.post.Post;
import com.dutact.web.features.event.admin.dtos.post.*;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "coverPhotoUrl", expression = "java(post.getCoverPhotos().get(0).getFileUrl())")
    @Mapping(target = "coverPhotoUrls", expression = "java(post.getCoverPhotos().stream().map(photo -> photo.getFileUrl()).toList())")
    PostDto toPostDto(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "event.id", source = "eventId")
    Post toPost(PostCreateDtoV2 postCreateDtoV2);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "event.id", source = "eventId")
    Post toPost(PostCreateDtoV1 postCreateDtoV1);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "event", ignore = true)
    void updatePost(@MappingTarget Post post, PostUpdateDtoV2 postUpdateDtoV2);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "coverPhotos", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "event", ignore = true)
    void updatePost(@MappingTarget Post post, PostUpdateDtoV1 postUpdateDtoV1);
}
