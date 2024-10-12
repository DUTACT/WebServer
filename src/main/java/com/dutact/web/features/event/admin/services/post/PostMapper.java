package com.dutact.web.features.event.admin.services.post;

import com.dutact.web.core.entities.post.Post;
import com.dutact.web.features.event.admin.dtos.post.PostCreateDto;
import com.dutact.web.features.event.admin.dtos.post.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    @Mapping(target = "eventId", source = "event.id")
    PostDto toPostDto(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "event.id", source = "eventId")
    Post toPost(PostCreateDto eventDto);
}
