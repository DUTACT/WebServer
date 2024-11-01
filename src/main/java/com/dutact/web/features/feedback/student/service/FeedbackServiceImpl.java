package com.dutact.web.features.feedback.student.service;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.core.entities.feedback.Feedback;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.core.repositories.FeedbackRepository;
import com.dutact.web.core.repositories.StudentRepository;
import com.dutact.web.core.specs.FeedbackSpecs;
import com.dutact.web.features.feedback.student.dtos.CreateFeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackQueryParams;
import com.dutact.web.features.feedback.student.dtos.UpdateFeedbackDto;
import com.dutact.web.storage.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final EventRepository eventRepository;
    private final StudentRepository studentRepository;
    private final FeedbackMapper feedbackMapper;
    private final UploadedFileMapper uploadedFileMapper;
    private final StorageService storageService;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository,
                               EventRepository eventRepository,
                               StudentRepository studentRepository,
                               FeedbackMapper feedbackMapper,
                               UploadedFileMapper uploadedFileMapper,
                               StorageService storageService) {
        this.feedbackRepository = feedbackRepository;
        this.eventRepository = eventRepository;
        this.studentRepository = studentRepository;
        this.feedbackMapper = feedbackMapper;
        this.uploadedFileMapper = uploadedFileMapper;
        this.storageService = storageService;
    }

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
        feedbackRepository.save(feedback);

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

        return feedbackRepository.findAll(spec)
                .stream()
                .map(feedbackMapper::toDto)
                .toList();
    }

    @Override
    public Optional<FeedbackDto> getFeedback(Integer feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .map(feedbackMapper::toDto);
    }

    @Override
    public FeedbackDto updateFeedback(Integer feedbackId, UpdateFeedbackDto updateFeedbackDto)
            throws NotExistsException {
        var feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new NotExistsException("The feedback does not exist"));

        feedbackMapper.updateFeedback(feedback, updateFeedbackDto);
        updateCoverPhoto(feedback, updateFeedbackDto);

        feedbackRepository.save(feedback);

        return feedbackMapper.toDto(feedback);
    }

    @Override
    public void deleteFeedback(Integer feedbackId) {
        feedbackRepository.deleteById(feedbackId);
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
}
