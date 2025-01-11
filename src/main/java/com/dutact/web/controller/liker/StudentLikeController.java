package com.dutact.web.controller.liker;

import com.dutact.web.dto.liker.StudentBasicInfoDto;
import com.dutact.web.service.liker.StudentLikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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