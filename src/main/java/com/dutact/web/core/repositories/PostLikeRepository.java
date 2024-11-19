package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    boolean existsByPostIdAndStudentId(Integer postId, Integer studentId);
    void deleteByPostIdAndStudentId(Integer postId, Integer studentId);
    long countByPostId(Integer postId);
    long countByPost_EventId(Integer eventId);
} 