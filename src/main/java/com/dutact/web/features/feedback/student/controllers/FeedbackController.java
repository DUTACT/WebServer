package com.dutact.web.features.feedback.student.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.feedback.student.dtos.*;
import com.dutact.web.features.feedback.student.service.FeedbackService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("StudentFeedbackController")
@RequestMapping("/api/student/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final StudentAccountService studentAccountService;

    public FeedbackController(FeedbackService feedbackService,
                              StudentAccountService studentAccountService) {
        this.feedbackService = feedbackService;
        this.studentAccountService = studentAccountService;
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDto>> getFeedbacks(
            @RequestParam(required = false) Integer eventId,
            @RequestParam(required = false) Integer studentId) {
        var params = new FeedbackQueryParams();
        params.setEventId(eventId);
        params.setStudentId(studentId);

        return ResponseEntity.ok(feedbackService.getFeedbacks(params));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDto> getFeedback(@PathVariable Integer id)
            throws NotExistsException {
        return ResponseEntity.ok(feedbackService.getFeedback(id)
                .orElseThrow(NotExistsException::new));
    }

    @PostMapping(path = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FeedbackDto> createFeedbackV1(
            @ModelAttribute CreateFeedbackDtoV1 createFeedbackDtoV1) throws NotExistsException {
        var studentId = studentAccountService.getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"));

        return ResponseEntity.ok(feedbackService.createFeedback(studentId, createFeedbackDtoV1));
    }

    @PostMapping(path = "/v2", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FeedbackDto> createFeedbackV2(
            @ModelAttribute CreateFeedbackDtoV2 createFeedbackDtoV2) throws NotExistsException {
        var studentId = studentAccountService.getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"));

        return ResponseEntity.ok(feedbackService.createFeedback(studentId, createFeedbackDtoV2));
    }

    @PatchMapping(path = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FeedbackDto> updateFeedbackV1(
            @PathVariable Integer id,
            @ModelAttribute UpdateFeedbackDtoV1 updateFeedbackDtoV1) throws NotExistsException {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, updateFeedbackDtoV1));
    }

    @PatchMapping(path = "/{id}/v2", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FeedbackDto> updateFeedbackV2(
            @PathVariable Integer id,
            @ModelAttribute UpdateFeedbackDtoV2 updateFeedbackDtoV2) throws NotExistsException {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, updateFeedbackDtoV2));
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Feedback deleted successfully")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Integer id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{feedbackId}/like")
    public ResponseEntity<Void> likeFeedback(@PathVariable Integer feedbackId) {
        try {
            var studentId = studentAccountService.getStudentId(SecurityContextUtils.getUsername())
                    .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"));
            feedbackService.likeFeedback(studentId, feedbackId);
            return ResponseEntity.ok().build();
        } catch (NotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{feedbackId}/like")
    public ResponseEntity<Void> unlikeFeedback(@PathVariable Integer feedbackId) {
        try {
            var studentId = studentAccountService.getStudentId(SecurityContextUtils.getUsername())
                    .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"));
            feedbackService.unlikeFeedback(studentId, feedbackId);
            return ResponseEntity.ok().build();
        } catch (NotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/liked")
    public ResponseEntity<?> getLikedFeedbacks() {
        try {
            var studentId = studentAccountService.getStudentId(SecurityContextUtils.getUsername())
                    .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"));
            return ResponseEntity.ok(feedbackService.getLikedFeedbacks(studentId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
