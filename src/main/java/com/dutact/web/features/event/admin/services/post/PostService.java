package com.dutact.web.features.event.admin.services.post;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.post.PostStatus;
import com.dutact.web.features.event.admin.dtos.post.PostCreateDto;
import com.dutact.web.features.event.admin.dtos.post.PostDto;
import com.dutact.web.features.event.admin.dtos.post.PostUpdateDto;

import java.util.Collection;
import java.util.Optional;

public interface PostService {
    PostDto createPost(PostCreateDto postDto);

    Collection<PostDto> getPosts(Integer eventId);

    Optional<PostDto> getPost(Integer postId);

    PostStatus updatePostStatus(Integer postId, PostStatus postStatus);

    PostDto updatePost(Integer postId, PostUpdateDto postUpdateDto) throws NotExistsException;

    void deletePost(Integer postId);

    boolean postExists(Integer eventId, Integer postId);
}
