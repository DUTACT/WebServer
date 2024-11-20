package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.feedback.FeedbackLike;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface FeedbackLikeRepository extends JpaRepository<FeedbackLike, Integer> {
    boolean existsByFeedbackIdAndStudentId(Integer feedbackId, Integer studentId);
    
    Optional<FeedbackLike> findByFeedbackIdAndStudentId(Integer feedbackId, Integer studentId);
    
    void deleteByFeedbackIdAndStudentId(Integer feedbackId, Integer studentId);
    
    Integer countByFeedbackId(Integer feedbackId);
    
    Collection<FeedbackLike> findAllByStudentId(Integer studentId, Sort sort);
} 