package com.dutact.web.event.post;

import com.dutact.web.common.notification.core.NotificationData;
import com.dutact.web.common.notification.core.NotificationDeliveryCentral;
import com.dutact.web.common.notification.core.data.NotificationType;
import com.dutact.web.data.repository.EventFollowRepository;
import com.dutact.web.data.repository.PostRepository;
import com.dutact.web.event.event.PostCreatedEvent;
import com.dutact.web.service.post.student.PostMapper;
import com.dutact.web.specs.EventFollowSpecs;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class StudentPostEventListener {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final EventFollowRepository eventFollowRepository;
    private final NotificationDeliveryCentral notificationDeliveryCentral;

    public StudentPostEventListener(PostRepository postRepository,
                                    PostMapper postMapper,
                                    EventFollowRepository eventFollowRepository,
                                    NotificationDeliveryCentral notificationDeliveryCentral) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.eventFollowRepository = eventFollowRepository;
        this.notificationDeliveryCentral = notificationDeliveryCentral;
    }

    @Async
    @SneakyThrows
    @EventListener
    @Transactional
    public void onPostCreated(PostCreatedEvent event) {
        var postOpt = postRepository.findById(event.postId());
        if (postOpt.isEmpty()) {
            log.warn("Cannot get post with id {}", event.postId());
            return;
        }

        var post = postOpt.get();
        var followerIds = eventFollowRepository
                .findAll(EventFollowSpecs.hasEventId(post.getEvent().getId())
                        .and(EventFollowSpecs.joinEvent()))
                .stream()
                .map(f -> f.getStudent().getId())
                .toList();

        var postDto = postMapper.toDto(post);

        var notificationData = new NotificationData();
        notificationData.setAccountIds(followerIds);
        notificationData.setDetails(postDto);
        notificationData.setNotificationType(NotificationType.POST_CREATED);
        notificationDeliveryCentral.sendNotification(notificationData);
    }
}
