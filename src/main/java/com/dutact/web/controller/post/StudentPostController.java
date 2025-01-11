package com.dutact.web.controller.post;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.auth.SecurityContextUtils;
import com.dutact.web.data.repository.StudentRepository;
import com.dutact.web.dto.post.student.PostDto;
import com.dutact.web.service.post.student.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/posts")
@AllArgsConstructor
public class StudentPostController {

    private final PostService postService;
    private final StudentRepository studentRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Integer id) {
        try {
            PostDto postDto = postService.getPost(id);
            return ResponseEntity.ok(postDto);
        } catch (NotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(required = false) Integer eventId) {
        return ResponseEntity.ok(postService.getPosts(eventId));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Integer postId) {
        try {
            var studentId = studentRepository.findByUsername(SecurityContextUtils.getUsername())
                    .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"))
                    .getId();
            postService.likePost(studentId, postId);
            return ResponseEntity.ok().build();
        } catch (NotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> unlikePost(@PathVariable Integer postId) {
        try {
            var studentId = studentRepository.findByUsername(SecurityContextUtils.getUsername())
                    .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"))
                    .getId();
            postService.unlikePost(studentId, postId);
            return ResponseEntity.ok().build();
        } catch (NotExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/liked")
    public ResponseEntity<?> getLikedPosts() {
        try {
            var studentId = studentRepository.findByUsername(SecurityContextUtils.getUsername())
                    .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"))
                    .getId();
            return ResponseEntity.ok(postService.getLikedPosts(studentId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
