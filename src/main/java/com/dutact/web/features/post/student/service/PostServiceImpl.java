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
    public PostDto getPost(Integer postId) throws NotExistsException {
        Optional<PostDto> post = postRepository.findById(postId)
                .flatMap(this::filterAndMapPost);
        if (post.isEmpty()) {
            throw new NotExistsException();
        }
        
        PostDto postDto = post.get();
        postDto.setLikeNumber(postLikeRepository.countByPostId(postId));
        
        // Get current student's like information
        String username = SecurityContextUtils.getUsername();
        studentRepository.findByUsername(username).ifPresent(student -> {
            postLikeRepository.findByPostIdAndStudentId(postId, student.getId())
                    .ifPresent(like -> postDto.setLikedAt(like.getLikedAt()));
        });
        
        return postDto;
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

        // Get current student for like information
        Integer currentStudentId = null;
        try {
            currentStudentId = studentRepository.findByUsername(SecurityContextUtils.getUsername())
                    .map(Student::getId)
                    .orElse(null);
        } catch (Exception ignored) {}

        final Integer studentId = currentStudentId;
        
        return posts.stream()
                .map(this::filterAndMapPost)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(postDto -> {
                    postDto.setLikeNumber(postLikeRepository.countByPostId(postDto.getId()));
                    if (studentId != null) {
                        postLikeRepository.findByPostIdAndStudentId(postDto.getId(), studentId)
                                .ifPresent(like -> postDto.setLikedAt(like.getLikedAt()));
                    }
                })
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

    @Override
    public Collection<PostDto> getLikedPosts(Integer studentId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "likedAt");
        Collection<PostLike> likes = postLikeRepository.findAllByStudentId(studentId, sort);
        
        return likes.stream()
                .map(like -> {
                    Post post = like.getPost();
                    Optional<PostDto> postDto = filterAndMapPost(post);
                    if (postDto.isPresent()) {
                        PostDto dto = postDto.get();
                        dto.setLikeNumber(postLikeRepository.countByPostId(post.getId()));
                        dto.setLikedAt(like.getLikedAt());
                        return Optional.of(dto);
                    }
                    return Optional.<PostDto>empty();
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
