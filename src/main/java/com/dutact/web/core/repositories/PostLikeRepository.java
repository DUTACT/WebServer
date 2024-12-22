package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.post.PostLike;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    boolean existsByPostIdAndStudentId(Integer postId, Integer studentId);
    void deleteByPostIdAndStudentId(Integer postId, Integer studentId);
    Collection<PostLike> findAllByStudentId(Integer studentId, Sort sort);
    Optional<PostLike> findByPostIdAndStudentId(Integer postId, Integer studentId);
    Integer countByPostId(Integer postId);
    
    @Query("SELECT pl.student FROM PostLike pl WHERE pl.post.id = :postId")
    List<Student> findStudentsByPostId(@Param("postId") Integer postId);
    
    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.event.organizer.id = :organizerId")
    long countByOrganizerId(@Param("organizerId") Integer organizerId);
} 