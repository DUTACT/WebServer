package com.dutact.web.features.activity.services;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.StudentActivity;
import com.dutact.web.core.repositories.StudentActivityRepository;
import com.dutact.web.features.activity.dto.StudentActivityDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
} 