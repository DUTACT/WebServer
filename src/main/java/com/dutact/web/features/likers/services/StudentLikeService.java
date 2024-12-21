package com.dutact.web.features.likers.services;

import com.dutact.web.core.repositories.FeedbackLikeRepository;
import com.dutact.web.core.repositories.PostLikeRepository;
import com.dutact.web.features.likers.dto.StudentBasicInfoDto;
import com.dutact.web.features.likers.mapper.StudentBasicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentLikeService {
    private final PostLikeRepository postLikeRepository;
    private final FeedbackLikeRepository feedbackLikeRepository;
    private final StudentBasicMapper studentBasicMapper;

    public List<StudentBasicInfoDto> getPostLikers(Integer postId) {
        return postLikeRepository.findStudentsByPostId(postId).stream()
            .map(studentBasicMapper::toBasicInfoDto)
            .collect(Collectors.toList());
    }

    public List<StudentBasicInfoDto> getFeedbackLikers(Integer feedbackId) {
        return feedbackLikeRepository.findStudentsByFeedbackId(feedbackId).stream()
            .map(studentBasicMapper::toBasicInfoDto)
            .collect(Collectors.toList());
    }
} 