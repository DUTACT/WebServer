package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.feedback.FeedbackLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackLikeRepository extends JpaRepository<FeedbackLike, Integer> {
    boolean existsByFeedbackIdAndStudentId(Integer feedbackId, Integer studentId);
    
    Optional<FeedbackLike> findByFeedbackIdAndStudentId(Integer feedbackId, Integer studentId);
    
    void deleteByFeedbackIdAndStudentId(Integer feedbackId, Integer studentId);
    
    Integer countByFeedbackId(Integer feedbackId);
    
    Collection<FeedbackLike> findAllByStudentId(Integer studentId, Sort sort);
    
    @Query("SELECT fl.student FROM FeedbackLike fl WHERE fl.feedback.id = :feedbackId")
    List<Student> findStudentsByFeedbackId(@Param("feedbackId") Integer feedbackId);
    
    @Query("SELECT COUNT(fl) FROM FeedbackLike fl WHERE fl.feedback.event.organizer.id = :organizerId")
    long countByOrganizerId(@Param("organizerId") Integer organizerId);
} 