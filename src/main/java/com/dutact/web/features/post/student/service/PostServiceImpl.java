package com.dutact.web.features.post.student.service;

import com.dutact.web.core.entities.post.Post;
import com.dutact.web.core.entities.post.PostStatus;
import com.dutact.web.core.repositories.PostRepository;
import com.dutact.web.features.post.student.dtos.PostDto;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository,
                           PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public Optional<PostDto> getPost(Integer postId) {
        return postRepository.findById(postId)
                .flatMap(this::filterAndMapPost);
    }

    @Override
    public Collection<PostDto> getPosts(@Nullable Integer eventId) {
        Collection<Post> posts;
        Sort sort = Sort.by(Sort.Direction.DESC, "postedAt");
        if (eventId == null) {
            posts = postRepository.findAll(sort);
        } else {
            posts = postRepository.findAllByEventId(eventId, sort);
        }

        return posts.stream()
                .map(this::filterAndMapPost)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<PostDto> filterAndMapPost(Post post) {
        return post.getStatus().getClass().equals(PostStatus.Public.class) ?
                Optional.of(postMapper.toDto(post)) :
                Optional.empty();
    }
}
