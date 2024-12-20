package com.dutact.web.features.likers;

import com.dutact.web.features.likers.dto.StudentBasicInfoDto;
import com.dutact.web.features.likers.services.StudentLikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudentLikeController {
    private final StudentLikeService studentLikeService;

    @GetMapping("/posts/{postId}/likers")
    @Operation(summary = "Get list of students who liked a post")
    public ResponseEntity<List<StudentBasicInfoDto>> getPostLikers(
            @PathVariable Integer postId) {
        return ResponseEntity.ok(studentLikeService.getPostLikers(postId));
    }

    @GetMapping("/feedbacks/{feedbackId}/likers")
    @Operation(summary = "Get list of students who liked a feedback")
    public ResponseEntity<List<StudentBasicInfoDto>> getFeedbackLikers(
            @PathVariable Integer feedbackId) {
        return ResponseEntity.ok(studentLikeService.getFeedbackLikers(feedbackId));
    }
} 