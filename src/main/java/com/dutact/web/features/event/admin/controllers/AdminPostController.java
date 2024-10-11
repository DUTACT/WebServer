package com.dutact.web.features.event.admin.controllers;

import com.azure.core.annotation.QueryParam;
import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.OrganizerService;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.core.entities.post.PostStatus;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.post.PostCreateDto;
import com.dutact.web.features.event.admin.dtos.post.PostDto;
import com.dutact.web.features.event.admin.services.event.EventService;
import com.dutact.web.features.event.admin.services.post.PostService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/admin/posts")
public class AdminPostController {

    private final EventService eventService;
    private final PostService postService;
    private final OrganizerService organizerService;

    public AdminPostController(EventService eventService, PostService postService, OrganizerService organizerService) {
        this.eventService = eventService;
        this.postService = postService;
        this.organizerService = organizerService;
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateDto postDto) {
        if (!canManageOwnPosts()) {
            return ResponseEntity.status(403).build();
        }

        int requestOrgId = organizerService.getOrganizerId(SecurityContextUtils.getUsername())
                .orElseThrow();
        EventDto event = eventService.getEvent(postDto.getEventId()).orElseThrow();
        if (event.getOrganizerId() != requestOrgId) {
            return ResponseEntity.status(403).build();
        }

        PostDto createdPost = postService.createPost(postDto);
        return ResponseEntity.created(new URI("/api/posts/" + createdPost.getId()))
                .body(createdPost);
    }

    @GetMapping()
    public ResponseEntity<Collection<PostDto>> getPosts(@RequestParam("event_id") Integer eventId) {
        if (SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER) && isEventOwner(eventId)) {
            return ResponseEntity.ok(postService.getPosts(eventId));
        } else if (SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            return ResponseEntity.ok(postService.getPosts(eventId));
        }
        return ResponseEntity.status(403).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer id) {
        PostDto post = postService.getPost(id).orElseThrow();
        EventDto event = eventService.getEvent(post.getEventId()).orElseThrow();
        if (SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER) && !isEventOwner(event.getId())) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PostStatus> updateEventStatus(@PathVariable Integer id,
                                                         @RequestBody PostStatus status) {
        if (!SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(postService.updatePostStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        PostDto post = postService.getPost(id).orElseThrow();
        EventDto event = eventService.getEvent(post.getEventId()).orElseThrow();
        if (!(canManageOwnPosts() && isEventOwner(event.getId()))) {
            return ResponseEntity.status(403).build();
        }

        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    private boolean canManageOwnPosts() {
        return SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER)
                || SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE);
    }

    private boolean isEventOwner(Integer eventId) {
        String username = SecurityContextUtils.getUsername();
        return organizerService.getOrganizerId(username)
                .map(orgId -> eventService.eventExists(orgId, eventId))
                .orElse(false);
    }
}
