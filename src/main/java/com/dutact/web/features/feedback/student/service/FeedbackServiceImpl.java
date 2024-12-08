package com.dutact.web.features.feedback.student.service;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.StudentActivity;
import com.dutact.web.core.entities.feedback.Feedback;
import com.dutact.web.core.entities.feedback.FeedbackLike;
import com.dutact.web.core.repositories.*;
import com.dutact.web.core.specs.FeedbackSpecs;
import com.dutact.web.features.activity.dto.ActivityType;
import com.dutact.web.features.activity.services.StudentActivityService;
import com.dutact.web.features.feedback.student.dtos.CreateFeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackQueryParams;
import com.dutact.web.features.feedback.student.dtos.UpdateFeedbackDto;
import com.dutact.web.storage.StorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final EventRepository eventRepository;
    private final StudentRepository studentRepository;
    private final FeedbackMapper feedbackMapper;
    private final UploadedFileMapper uploadedFileMapper;
    private final StorageService storageService;
    private final PostLikeRepository postLikeRepository;
    private final StudentAccountService studentAccountService;
    private final FeedbackLikeRepository feedbackLikeRepository;
    private final StudentActivityService studentActivityService;

    @Override
    public FeedbackDto createFeedback(Integer studentId, CreateFeedbackDto createFeedbackDto) throws NotExistsException {
        if (!eventRepository.existsById(createFeedbackDto.getEventId())) {
            throw new NotExistsException("The event does not exist");
        }

        var feedback = feedbackMapper.toFeedback(createFeedbackDto);

        if (createFeedbackDto.getCoverPhoto() != null) {
            var uploadFileResult = storageService
                    .uploadFile(createFeedbackDto.getCoverPhoto(),
                            FilenameUtils.getExtension(createFeedbackDto.getCoverPhoto().getOriginalFilename()));
            feedback.setCoverPhoto(uploadedFileMapper.toUploadedFile(uploadFileResult));
        }

        feedback.setStudent(studentRepository.getReferenceById(studentId));
        feedback.setEvent(eventRepository.getReferenceById(createFeedbackDto.getEventId()));
        feedback.setPostedAt(LocalDateTime.now());
        feedback = feedbackRepository.save(feedback);

        StudentActivity activity = new StudentActivity();
        activity.setType(ActivityType.FEEDBACK_CREATE);
        activity.setEvent(feedback.getEvent());
        activity.setFeedbackId(feedback.getId());
        studentActivityService.recordActivity(studentId, activity);

        return feedbackMapper.toDto(feedback);
    }

    @Override
    public List<FeedbackDto> getFeedbacks(FeedbackQueryParams feedbackQueryParams) {
        Specification<Feedback> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        if (feedbackQueryParams.getStudentId() != null) {
            spec = spec.and(FeedbackSpecs.hasStudentId(feedbackQueryParams.getStudentId()));
        }

        if (feedbackQueryParams.getEventId() != null) {
            spec = spec.and(FeedbackSpecs.hasEventId(feedbackQueryParams.getEventId()));
        }

        spec = spec.and(FeedbackSpecs.joinEvent());
        spec = spec.and(FeedbackSpecs.joinStudent());
        spec = spec.and(FeedbackSpecs.orderByPostedAt(false));

        var studentId = studentAccountService.getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"));

        return feedbackRepository.findAll(spec)
                .stream()
                .map(feedbackMapper::toDto)
                .peek(f -> {
                    f.setLikeNumber(feedbackLikeRepository.countByFeedbackId(f.getId()));
                    Optional<FeedbackLike> like = feedbackLikeRepository.findByFeedbackIdAndStudentId(f.getId(), studentId);
                    if (like.isPresent()){
                        f.setLikedAt(like.get().getLikedAt());
                    } else {
                        f.setLikedAt(null);
                    }
                })

                .toList();
    }

    @Override
    public Optional<FeedbackDto> getFeedback(Integer feedbackId) {
        Optional<FeedbackDto> feedbackDto = feedbackRepository.findById(feedbackId)
                .map(feedbackMapper::toDto);
        var studentId = studentAccountService.getStudentId(SecurityContextUtils.getUsername())
                .orElseThrow(() -> new RuntimeException("The request is associated with a non-existent student"));
        if (feedbackDto.isPresent()){
            feedbackDto.get().setLikeNumber(feedbackLikeRepository.countByFeedbackId(feedbackId));
            Optional<FeedbackLike> like = feedbackLikeRepository.findByFeedbackIdAndStudentId(feedbackId, studentId);
            if (like.isPresent()){
                feedbackDto.get().setLikedAt(like.get().getLikedAt());
            } else {
                feedbackDto.get().setLikedAt(null);
            }
        }
        return feedbackDto;
    }

    @Override
    public FeedbackDto updateFeedback(Integer feedbackId, UpdateFeedbackDto updateFeedbackDto)
            throws NotExistsException {
        var feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new NotExistsException("The feedback does not exist"));

        feedbackMapper.updateFeedback(feedback, updateFeedbackDto);
        if (updateFeedbackDto.isDeleteCoverPhoto()) {
            deleteCoverPhoto(feedback);
        }
        updateCoverPhoto(feedback, updateFeedbackDto);

        feedbackRepository.save(feedback);

        return feedbackMapper.toDto(feedback);
    }

    @Override
    public void deleteFeedback(Integer feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    void deleteCoverPhoto(Feedback feedback) {
        if (feedback.getCoverPhoto() != null) {
            storageService.deleteFile(feedback.getCoverPhoto().getFileId());
            feedback.setCoverPhoto(null);
        }
    }

    void updateCoverPhoto(Feedback feedback, UpdateFeedbackDto updateFeedbackDto) {
        if (updateFeedbackDto.getCoverPhoto() != null) {
            var coverPhoto = feedback.getCoverPhoto();
            if (coverPhoto != null) {
                var uploadFileResult = storageService.updateFile(coverPhoto.getFileId(), updateFeedbackDto.getCoverPhoto());
                feedback.setCoverPhoto(uploadedFileMapper.toUploadedFile(uploadFileResult));
            } else {
                var coverPhotoExtension = FilenameUtils
                        .getExtension(updateFeedbackDto.getCoverPhoto().getOriginalFilename());

                var uploadFileResult = storageService
                        .uploadFile(updateFeedbackDto.getCoverPhoto(), coverPhotoExtension);

                feedback.setCoverPhoto(uploadedFileMapper.toUploadedFile(uploadFileResult));
            }
        }
    }

    @Override
public void likeFeedback(Integer studentId, Integer feedbackId) throws NotExistsException {
    Feedback feedback = feedbackRepository.findById(feedbackId)
        .orElseThrow(() -> new NotExistsException("Feedback not found"));
    Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new NotExistsException("Student not found"));

    if (!feedbackLikeRepository.existsByFeedbackIdAndStudentId(feedbackId, studentId)) {
        FeedbackLike like = new FeedbackLike();
        like.setFeedback(feedback);
        like.setStudent(student);
        like.setLikedAt(LocalDateTime.now());
        feedbackLikeRepository.save(like);

        StudentActivity activity = new StudentActivity();
        activity.setType(ActivityType.FEEDBACK_LIKE);
        activity.setEvent(feedback.getEvent());
        activity.setFeedbackId(feedbackId);
        studentActivityService.recordActivity(studentId, activity);
    }
}

@Override
@Transactional
public void unlikeFeedback(Integer studentId, Integer feedbackId) throws NotExistsException {
    if (!feedbackRepository.existsById(feedbackId)) {
        throw new NotExistsException("Feedback not found");
    }
    if (!studentRepository.existsById(studentId)) {
        throw new NotExistsException("Student not found");
    }

    studentActivityService.removeFeedbackLikeActivity(studentId, feedbackId);
    feedbackLikeRepository.deleteByFeedbackIdAndStudentId(feedbackId, studentId);
}

@Override
public Collection<FeedbackDto> getLikedFeedbacks(Integer studentId) {
    Sort sort = Sort.by(Sort.Direction.DESC, "likedAt");
    Collection<FeedbackLike> likes = feedbackLikeRepository.findAllByStudentId(studentId, sort);
    
    return likes.stream()
            .map(like -> {
                Feedback feedback = like.getFeedback();
                FeedbackDto dto = feedbackMapper.toDto(feedback);
                dto.setLikeNumber(feedbackLikeRepository.countByFeedbackId(feedback.getId()));
                dto.setLikedAt(like.getLikedAt());
                return dto;
            })
            .collect(Collectors.toList());
}
}
