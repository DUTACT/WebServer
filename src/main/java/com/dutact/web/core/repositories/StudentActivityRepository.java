package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.StudentActivity;
import com.dutact.web.features.activity.dto.ActivityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentActivityRepository extends JpaRepository<StudentActivity, Integer> {
    Page<StudentActivity> findAllByStudentIdOrderByCreatedAtDesc(Integer studentId, Pageable pageable);
    void deleteByStudentIdAndTypeAndEventId(Integer studentId, ActivityType type, Integer eventId);
    void deleteByStudentIdAndTypeAndPostId(Integer studentId, ActivityType type, Integer postId);
    void deleteByStudentIdAndTypeAndFeedbackId(Integer studentId, ActivityType type, Integer feedbackId);
} 