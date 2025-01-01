package com.dutact.web.features.newsfeed.student.service;

import com.dutact.web.core.entities.EventFollow;
import com.dutact.web.core.entities.feedback.Feedback;
import com.dutact.web.core.entities.feedback.FeedbackLike;
import com.dutact.web.core.entities.post.Post;
import com.dutact.web.core.entities.post.PostLike;
import com.dutact.web.core.repositories.EventFollowRepository;
import com.dutact.web.core.repositories.FeedbackRepository;
import com.dutact.web.core.repositories.PostRepository;
import com.dutact.web.core.repositories.PostLikeRepository;
import com.dutact.web.core.repositories.FeedbackLikeRepository;
import com.dutact.web.features.newsfeed.student.dtos.NewsfeedItemDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Collection;

@Service
public class NewsfeedServiceImpl implements NewsfeedService {
    private final FeedbackRepository feedbackRepository;
    private final PostRepository postRepository;
    private final NewsfeedMapper newsfeedMapper;
    private final EventFollowRepository eventFollowRepository;
    private final PostLikeRepository postLikeRepository;
    private final FeedbackLikeRepository feedbackLikeRepository;

    public NewsfeedServiceImpl(FeedbackRepository feedbackRepository,
                               PostRepository postRepository,
                               NewsfeedMapper newsfeedMapper,
                               EventFollowRepository eventFollowRepository,
                               PostLikeRepository postLikeRepository,
                               FeedbackLikeRepository feedbackLikeRepository) {
        this.feedbackRepository = feedbackRepository;
        this.postRepository = postRepository;
        this.newsfeedMapper = newsfeedMapper;
        this.eventFollowRepository = eventFollowRepository;
        this.postLikeRepository = postLikeRepository;
        this.feedbackLikeRepository = feedbackLikeRepository;
    }

    @Override
    public List<NewsfeedItemDto> getFollowedNewsfeed(Integer studentId) {
        List<EventFollow> followedEventIds = eventFollowRepository.findAllByStudentId(studentId);
        List<Post> posts = postRepository.findAllByEventIdIn(followedEventIds.stream().map(f -> f.getEvent().getId()).toList());
        List<Feedback> feedbacks = feedbackRepository.findAllByEventIdIn(followedEventIds.stream().map(f -> f.getEvent().getId()).toList());
        List<NewsfeedItemDto> newsfeedItemDtos = new ArrayList<>();

        Collection<PostLike> postLikes = postLikeRepository.findAll(
                Sort.by(Sort.Direction.DESC, "likedAt"));
        Collection<FeedbackLike> feedbackLikes = feedbackLikeRepository.findAll(
                Sort.by(Sort.Direction.DESC, "likedAt"));
        for (Post post : posts) {
            NewsfeedItemDto newsfeedItemDto = newsfeedMapper.toPostDto(post);
            List<PostLike> postLike = postLikes.stream().filter(p -> p.getPost().getId().equals(post.getId())).toList();
            List<PostLike> postLikeOfUser = postLikes.stream().filter(p -> p.getPost().getId().equals(post.getId()) && p.getStudent().getId().equals(studentId)).toList();
            newsfeedItemDto.setLikeNumber(postLike.size());
            if(!postLikeOfUser.isEmpty()){
                newsfeedItemDto.setLikedAt(postLikeOfUser.get(0).getLikedAt());
            }
            newsfeedItemDtos.add(newsfeedItemDto);
        }
        
        for (Feedback feedback : feedbacks) {
            NewsfeedItemDto newsfeedItemDto = newsfeedMapper.toFeedbackDto(feedback);
            List<FeedbackLike> feedbackLike = feedbackLikes.stream().filter(p -> p.getFeedback().getId().equals(feedback.getId())).toList();
            List<FeedbackLike> feedbackLikeOfUser = feedbackLike.stream().filter(p -> p.getFeedback().getId().equals(feedback.getId()) && p.getStudent().getId().equals(studentId)).toList();
            newsfeedItemDto.setLikeNumber(feedbackLike.size());
            if(!feedbackLikeOfUser.isEmpty()){
                newsfeedItemDto.setLikedAt(feedbackLikeOfUser.get(0).getLikedAt());
            }
            newsfeedItemDtos.add(newsfeedItemDto);
        }
        
        Collections.sort(newsfeedItemDtos);
        Collections.reverse(newsfeedItemDtos);
        return newsfeedItemDtos;
    }

    @Override
    public List<NewsfeedItemDto> getLikedNewsfeed(Integer studentId) {
        List<NewsfeedItemDto> newsfeedItemDtos = new ArrayList<>();
        
        // Get liked posts
        Collection<PostLike> postLikes = postLikeRepository.findAllByStudentId(studentId,
            Sort.by(Sort.Direction.DESC, "likedAt"));
        for (PostLike like : postLikes) {
            Post post = like.getPost();
            NewsfeedItemDto.NewsfeedPostDto postDto = newsfeedMapper.toPostDto(post);
            postDto.setLikedAt(like.getLikedAt());
            postDto.setLikeNumber(postLikeRepository.countByPostId(postDto.getId()));
            newsfeedItemDtos.add(postDto);
        }

        // Get liked feedbacks
        Collection<FeedbackLike> feedbackLikes = feedbackLikeRepository.findAllByStudentId(studentId,
            Sort.by(Sort.Direction.DESC, "likedAt"));
        for (FeedbackLike like : feedbackLikes) {
            Feedback feedback = like.getFeedback();
            NewsfeedItemDto.NewsfeedFeedbackDto feedbackDto = newsfeedMapper.toFeedbackDto(feedback);
            feedbackDto.setLikedAt(like.getLikedAt());
            feedbackDto.setLikeNumber(feedbackLikeRepository.countByFeedbackId(feedbackDto.getId()));
            newsfeedItemDtos.add(feedbackDto);
        }

        Collections.sort(newsfeedItemDtos);
        Collections.reverse(newsfeedItemDtos);
        return newsfeedItemDtos;
    }
}
