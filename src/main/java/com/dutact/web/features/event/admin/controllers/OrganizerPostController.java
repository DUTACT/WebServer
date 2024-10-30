package com.dutact.web.features.event.admin.controllers;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.OrganizerAccountService;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.common.api.exceptions.ForbiddenException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.post.PostStatus;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.event.EventUpdateDto;
import com.dutact.web.features.event.admin.dtos.post.PostCreateDto;
import com.dutact.web.features.event.admin.dtos.post.PostDto;
import com.dutact.web.features.event.admin.dtos.post.PostUpdateDto;
import com.dutact.web.features.event.admin.services.event.EventService;
import com.dutact.web.features.event.admin.services.post.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin/posts")
public class OrganizerPostController {
    private final EventService eventService;
    private final PostService postService;
    private final OrganizerAccountService organizerAccountService;

    public OrganizerPostController(EventService eventService,
                                   PostService postService,
                                   OrganizerAccountService organizerAccountService) {
        this.eventService = eventService;
        this.postService = postService;
        this.organizerAccountService = organizerAccountService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostDto> createPost(@ModelAttribute PostCreateDto postDto)
            throws ForbiddenException, URISyntaxException {
        if (!canManageOwnPosts()) {
            throw new ForbiddenException("This account does not have permission to create posts");
        }

        int requestOrgId = organizerAccountService.getOrganizerId(SecurityContextUtils.getUsername())
                .orElseThrow();
        EventDto event = eventService.getEvent(postDto.getEventId()).orElseThrow();
        if (event.getOrganizer().getId() != requestOrgId) {
            throw new ForbiddenException("This account does not have permission to create posts for this event");
        }

        PostDto createdPost = postService.createPost(postDto);
        return ResponseEntity.created(new URI("/api/posts/" + createdPost.getId()))
                .body(createdPost);
    }

    @GetMapping
    public ResponseEntity<Collection<PostDto>> getPosts(@RequestParam("event-id") Integer eventId)
            throws ForbiddenException {
        if (SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER) && isEventOwner(eventId) ||
                SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            return ResponseEntity.ok(postService.getPosts(eventId));
        }

        throw new ForbiddenException("This account does not have permission to view posts for this event");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable("postId") Integer id) throws ForbiddenException {
        PostDto post = postService.getPost(id).orElseThrow();
        EventDto event = eventService.getEvent(post.getEventId()).orElseThrow();
        if (SecurityContextUtils.hasRole(Role.EVENT_ORGANIZER) && !isEventOwner(event.getId())) {
            throw new ForbiddenException("This account does not have permission to view this post");
        }

        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}/status")
    public ResponseEntity<PostStatus> updateEventStatus(@PathVariable("postId") Integer id,
                                                        @RequestBody PostStatus status) throws ForbiddenException {
        if (!SecurityContextUtils.hasRole(Role.STUDENT_AFFAIRS_OFFICE)) {
            throw new ForbiddenException("This account does not have permission to update post status");
        }

        return ResponseEntity.ok(postService.updatePostStatus(id, status));
    }

    @PatchMapping(path = "/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PostDto> updatePost(
            @PathVariable("postId") Integer postId,
            @ModelAttribute PostUpdateDto postUpdateDto) throws ForbiddenException, NotExistsException {
        PostDto post = postService.getPost(postId).orElseThrow(NotExistsException::new);
        EventDto event = eventService.getEvent(post.getEventId()).orElseThrow();

        if (!(canManageOwnPosts() && isEventOwner(event.getId()))) {
            throw new ForbiddenException("This account does not have permission to update this post");
        }

        return ResponseEntity.ok(postService.updatePost(postId, postUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id)
            throws ForbiddenException, NotExistsException {
        PostDto post = postService.getPost(id).orElseThrow(NotExistsException::new);
        EventDto event = eventService.getEvent(post.getEventId()).orElseThrow();
        if (!(canManageOwnPosts() && isEventOwner(event.getId()))) {
            throw new ForbiddenException("This account does not have permission to delete this post");
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
        return organizerAccountService.getOrganizerId(username)
                .map(orgId -> eventService.eventExists(orgId, eventId))
                .orElse(false);
    }
}
