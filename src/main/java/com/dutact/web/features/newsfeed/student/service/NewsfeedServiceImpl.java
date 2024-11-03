package com.dutact.web.features.newsfeed.student.service;

import com.dutact.web.core.entities.feedback.Feedback;
import com.dutact.web.core.entities.post.Post;
import com.dutact.web.core.repositories.EventFollowRepository;
import com.dutact.web.core.repositories.FeedbackRepository;
import com.dutact.web.core.repositories.PostRepository;
import com.dutact.web.features.newsfeed.student.dtos.NewsfeedItemDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NewsfeedServiceImpl implements NewsfeedService {
    private final FeedbackRepository feedbackRepository;
    private final PostRepository postRepository;
    private final NewsfeedMapper newsfeedMapper;
    private final EventFollowRepository eventFollowRepository;

    public NewsfeedServiceImpl(FeedbackRepository feedbackRepository,
                               PostRepository postRepository,
                               NewsfeedMapper newsfeedMapper,
                               EventFollowRepository eventFollowRepository) {
        this.feedbackRepository = feedbackRepository;
        this.postRepository = postRepository;
        this.newsfeedMapper = newsfeedMapper;
        this.eventFollowRepository = eventFollowRepository;
    }

    @Override
    public List<NewsfeedItemDto> getFollowedNewsfeed(Integer studentId) {
        List<Integer> followedEventIds = eventFollowRepository.findAllByStudentId(studentId)
                .stream().map(eventFollow -> eventFollow.getEvent().getId())
                .toList();
        List<Post> posts = postRepository.findAllByEventIdIn(followedEventIds);
        List<Feedback> feedbacks = feedbackRepository.findAllByEventIdIn(followedEventIds);
        List<NewsfeedItemDto> newsfeedItemDtos = new ArrayList<>();
        for (Post post : posts) {
            NewsfeedItemDto newsfeedItemDto = newsfeedMapper.toPostDto(post);
            newsfeedItemDtos.add(newsfeedItemDto);
        }
        for (Feedback feedback : feedbacks) {
            NewsfeedItemDto newsfeedItemDto = newsfeedMapper.toFeedbackDto(feedback);
            newsfeedItemDtos.add(newsfeedItemDto);
        }
        Collections.sort(newsfeedItemDtos);
        Collections.reverse(newsfeedItemDtos);
        return newsfeedItemDtos;
    }
}
