package com.dutact.web.service.activity;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.data.entity.Student;
import com.dutact.web.data.entity.StudentActivity;
import com.dutact.web.data.repository.StudentActivityRepository;
import com.dutact.web.dto.activity.ActivityType;
import com.dutact.web.dto.activity.StudentActivityDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StudentActivityServiceImpl implements StudentActivityService {
    private final StudentActivityRepository activityRepository;
    private final StudentActivityMapper activityMapper;

    @Override
    public PageResponse<StudentActivityDto> getStudentActivities(Integer studentId, Integer page, Integer pageSize) {
        Page<StudentActivity> activities = activityRepository.findAllByStudentIdOrderByCreatedAtDesc(
                studentId,
                PageRequest.of(page - 1, pageSize)
        );

        return PageResponse.of(activities, activityMapper::toDto);
    }

    @Override
    public void recordActivity(Integer studentId, StudentActivity activity) {
        activity.setStudent(new Student(studentId));
        activityRepository.save(activity);
    }

    @Override
    @Transactional
    public void removeActivity(Integer studentId, ActivityType type, Integer eventId) {
        if (type != ActivityType.EVENT_FOLLOW && type != ActivityType.EVENT_REGISTER) {
            throw new IllegalArgumentException("This method should only be used for event-related activities");
        }
        activityRepository.deleteByStudentIdAndTypeAndEventId(studentId, type, eventId);
    }

    @Override
    @Transactional
    public void removePostLikeActivity(Integer studentId, Integer postId) {
        activityRepository.deleteByStudentIdAndTypeAndPostId(studentId, ActivityType.POST_LIKE, postId);
    }

    @Override
    @Transactional
    public void removeFeedbackLikeActivity(Integer studentId, Integer feedbackId) {
        activityRepository.deleteByStudentIdAndTypeAndFeedbackId(studentId, ActivityType.FEEDBACK_LIKE, feedbackId);
    }
} 