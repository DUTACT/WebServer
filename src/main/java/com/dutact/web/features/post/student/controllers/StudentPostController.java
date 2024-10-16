package com.dutact.web.features.post.student.controllers;

import com.dutact.web.features.post.student.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/posts")
public class StudentPostController {

    private final PostService postService;

    public StudentPostController(PostService postService) {

        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Integer id) {
        return postService.getPost(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(required = false) Integer eventId) {
        return ResponseEntity.ok(postService.getPosts(eventId));
    }
}
