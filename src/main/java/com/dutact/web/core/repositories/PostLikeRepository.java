package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.post.PostLike;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    boolean existsByPostIdAndStudentId(Integer postId, Integer studentId);
    void deleteByPostIdAndStudentId(Integer postId, Integer studentId);
    Collection<PostLike> findAllByStudentId(Integer studentId, Sort sort);
    Optional<PostLike> findByPostIdAndStudentId(Integer postId, Integer studentId);
    Integer countByPostId(Integer postId);
} 