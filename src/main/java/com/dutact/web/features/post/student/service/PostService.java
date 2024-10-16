package com.dutact.web.features.post.student.service;

import com.dutact.web.features.post.student.dtos.PostDto;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.Optional;

public interface PostService {
    Optional<PostDto> getPost(Integer postId);

    Collection<PostDto> getPosts(@Nullable Integer eventId);
}
