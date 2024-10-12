package com.dutact.web.features.event.admin.services.post;

import com.dutact.web.core.entities.post.PostStatus;
import com.dutact.web.features.event.admin.dtos.post.PostCreateDto;
import com.dutact.web.features.event.admin.dtos.post.PostDto;

import java.util.Collection;
import java.util.Optional;

public interface PostService {
    PostDto createPost(PostCreateDto postDto);

    Collection<PostDto> getPosts(Integer eventId);

    Optional<PostDto> getPost(Integer postId);

    PostStatus updatePostStatus(Integer postId, PostStatus postStatus);

    void deletePost(Integer postId);

    boolean postExists(Integer eventId, Integer postId);
}
