package com.dutact.web.features.newsfeed.student.service;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.core.entities.EventFollow;
import com.dutact.web.core.entities.feedback.Feedback;
import com.dutact.web.core.entities.post.Post;
import com.dutact.web.core.repositories.*;
import com.dutact.web.core.specs.FeedbackSpecs;
import com.dutact.web.features.feedback.student.dtos.CreateFeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackQueryParams;
import com.dutact.web.features.feedback.student.dtos.UpdateFeedbackDto;
import com.dutact.web.features.feedback.student.service.FeedbackMapper;
import com.dutact.web.features.feedback.student.service.FeedbackService;
import com.dutact.web.features.newsfeed.student.dtos.NewsfeedElementDto;
import com.dutact.web.storage.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public List<NewsfeedElementDto> getFollowedNewsfeed(Integer studentId) {
        List<Integer> followedEventIds = eventFollowRepository.findAllByStudentId(studentId)
                .stream().map(eventFollow -> eventFollow.getEvent().getId())
                .toList();
        List<Post> posts = postRepository.findAllByEventIdIn(followedEventIds);
        List<Feedback> feedbacks = feedbackRepository.findAllByEventIdIn(followedEventIds);
        List<NewsfeedElementDto> newsfeedElementDtos = new ArrayList<>();
        for (Post post : posts) {
            NewsfeedElementDto newsfeedElementDto = newsfeedMapper.toPostDto(post);
            newsfeedElementDtos.add(newsfeedElementDto);
        }
        for (Feedback feedback : feedbacks) {
            NewsfeedElementDto newsfeedElementDto = newsfeedMapper.toFeedbackDto(feedback);
            newsfeedElementDtos.add(newsfeedElementDto);
        }
        Collections.sort(newsfeedElementDtos);
        Collections.reverse(newsfeedElementDtos);
        return newsfeedElementDtos;
    }
}
