package com.dutact.web.features.event.admin.services.post;

import com.dutact.web.core.entities.post.Post;
import com.dutact.web.core.entities.post.PostStatus;
import com.dutact.web.core.repositories.PostRepository;
import com.dutact.web.features.event.admin.dtos.post.PostCreateDto;
import com.dutact.web.features.event.admin.dtos.post.PostDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service("adminPostService")
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    public PostServiceImpl(PostMapper postMapper, PostRepository postRepository) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostCreateDto postDto) {
        Post post = postMapper.toPost(postDto);
        post.setStatus(new PostStatus.Public());

        Post savedPost = postRepository.save(post);

        return postMapper.toPostDto(savedPost);
    }

    @Override
    public Collection<PostDto> getPosts(Integer eventId) {
        return postRepository.findAllByEventId(eventId).stream().map(postMapper::toPostDto).toList();
    }

    @Override
    public Optional<PostDto> getPost(Integer postId) {
        return postRepository.findById(postId).map(postMapper::toPostDto);
    }

    @Override
    public PostStatus updatePostStatus(Integer postId, PostStatus postStatus) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setStatus(postStatus);
        postRepository.save(post);

        return postStatus;
    }

    @Override
    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public boolean postExists(Integer eventId, Integer postId) {
        return postRepository.existsByIdAndEventId(eventId, postId);
    }
}
