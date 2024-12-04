package com.dutact.web.features.activity;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.features.activity.dto.StudentActivityDto;
import com.dutact.web.features.activity.services.StudentActivityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StudentActivityController {
    private final StudentActivityService activityService;
    private final StudentAccountService studentAccountService;

    @GetMapping("/api/student/activities")
    @Operation(summary = "Get student activities", description = "Retrieves a paginated list of student activities")
    public ResponseEntity<PageResponse<StudentActivityDto>> getActivities(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        Integer studentId = studentAccountService
            .getStudentId(SecurityContextUtils.getUsername())
            .orElseThrow(() -> new IllegalStateException("Student not found"));

        return ResponseEntity.ok(activityService.getStudentActivities(studentId, page, pageSize));
    }
} 