package com.dutact.web.features.post.student.service;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.post.Post;
import com.dutact.web.core.entities.post.PostStatus;
import com.dutact.web.core.repositories.PostRepository;
import com.dutact.web.core.repositories.StudentRepository;
import com.dutact.web.features.post.student.dtos.PostDto;
import com.dutact.web.core.entities.post.PostLike;
import com.dutact.web.core.repositories.PostLikeRepository;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostLikeRepository postLikeRepository;
    private final StudentRepository studentRepository;

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

    @Override
    public void likePost(Integer studentId, Integer postId) throws NotExistsException {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new NotExistsException("Post not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotExistsException("Student not found"));

        if (!postLikeRepository.existsByPostIdAndStudentId(postId, studentId)) {
            PostLike like = new PostLike();
            like.setPost(post);
            like.setStudent(student);
            like.setLikedAt(LocalDateTime.now());
            postLikeRepository.save(like);
        }
    }

    @Override
    @Transactional
    public void unlikePost(Integer studentId, Integer postId) throws NotExistsException {
        if (!postRepository.existsById(postId)) {
            throw new NotExistsException("Post not found");
        }
        if (!studentRepository.existsById(studentId)) {
            throw new NotExistsException("Student not found");
        }
        
        postLikeRepository.deleteByPostIdAndStudentId(postId, studentId);
    }
}
