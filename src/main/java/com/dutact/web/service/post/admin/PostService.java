package com.dutact.web.service.post.admin;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.post.admin.*;
import com.dutact.web.data.entity.post.PostStatus;

import java.util.Collection;
import java.util.Optional;

public interface PostService {
    PostDto createPost(PostCreateDtoV2 postCreateDtoV2);

    PostDto createPost(PostCreateDtoV1 postCreateDtoV1);

    Collection<PostDto> getPosts(Integer eventId);

    Optional<PostDto> getPost(Integer postId);

    PostStatus updatePostStatus(Integer postId, PostStatus postStatus);

    PostDto updatePost(Integer postId, PostUpdateDtoV2 postUpdateDtoV2) throws NotExistsException;

    PostDto updatePost(Integer postId, PostUpdateDtoV1 postUpdateDtoV1) throws NotExistsException;

    void deletePost(Integer postId);

    boolean postExists(Integer eventId, Integer postId);
}
