package com.dutact.web.controller.newsfeed;

import com.dutact.web.common.auth.SecurityContextUtils;
import com.dutact.web.dto.newsfeed.NewsfeedItemDto;
import com.dutact.web.service.auth.StudentAccountService;
import com.dutact.web.service.newsfeed.NewsfeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("StudentNewsfeedController")
@RequestMapping("/api/student/newsfeeds")
public class NewsfeedController {
    private final NewsfeedService newsfeedService;
    private final StudentAccountService studentAccountService;

    public NewsfeedController(NewsfeedService newsfeedService,
                              StudentAccountService studentAccountService) {
        this.newsfeedService = newsfeedService;
        this.studentAccountService = studentAccountService;
    }

    @GetMapping
    public ResponseEntity<List<NewsfeedItemDto>> getNewsfeed() {
        var studentId = studentAccountService.getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"));

        return ResponseEntity.ok(newsfeedService.getFollowedNewsfeed(studentId));
    }

    @GetMapping("/liked")
    public ResponseEntity<List<NewsfeedItemDto>> getLikedNewsfeed() {
        try {
            var studentId = studentAccountService.getStudentId(SecurityContextUtils.getUsername())
                    .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"));
            return ResponseEntity.ok(newsfeedService.getLikedNewsfeed(studentId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
