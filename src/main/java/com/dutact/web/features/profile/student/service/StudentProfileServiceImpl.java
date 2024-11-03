package com.dutact.web.features.profile.student.service;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.auth.factors.StudentAccountService;
import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.event.CannotChangeStatusException;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.repositories.EventRepository;
import com.dutact.web.core.repositories.OrganizerRepository;
import com.dutact.web.core.repositories.StudentRepository;
import com.dutact.web.core.specs.EventSpecs;
import com.dutact.web.features.event.admin.dtos.event.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.event.EventUpdateDto;
import com.dutact.web.features.profile.student.dtos.StudentProfileDto;
import com.dutact.web.features.profile.student.dtos.StudentProfileUpdateDto;
import com.dutact.web.storage.StorageService;
import com.dutact.web.storage.UploadFileResult;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("studentProfileService")
public class StudentProfileServiceImpl implements StudentProfileService {
    private final StudentProfileMapper studentProfileMapper;
    private final UploadedFileMapper uploadedFileMapper;
    private final StudentRepository studentRepository;
    private final StorageService storageService;

    public StudentProfileServiceImpl(StudentProfileMapper studentProfileMapper,
                                     UploadedFileMapper uploadedFileMapper,
                                     StudentRepository studentRepository,
                                     StorageService storageService) {
        this.studentProfileMapper = studentProfileMapper;
        this.uploadedFileMapper = uploadedFileMapper;
        this.studentRepository = studentRepository;
        this.storageService = storageService;
    }

    @Override
    public StudentProfileDto getProfile(Integer studentId) throws NotExistsException {
        return studentProfileMapper.toProfileDto(studentRepository.findById(studentId).orElseThrow(NotExistsException::new));
    }

    @Override
    public void updateProfile(Integer studentId, StudentProfileUpdateDto studentProfileDto) throws NotExistsException, ConflictException {
        Student student = studentRepository.findById(studentId).orElseThrow(NotExistsException::new);
        if (!isProfileOwner(studentId)) {
            throw new ConflictException();
        }
        studentProfileMapper.updateProfile(student, studentProfileDto);

        UploadFileResult uploadFileResult = writeFile(studentProfileDto.getAvatar());
        student.setAvatar(uploadedFileMapper.toUploadedFile(uploadFileResult));

        studentRepository.save(student);
    }


    private boolean isProfileOwner(Integer profileId) throws NotExistsException {
        String username = SecurityContextUtils.getUsername();

        Optional<Student> studentOpt = studentRepository.findByUsername(username);
        if (studentOpt.isEmpty()) {
            throw new NotExistsException();
        }

        return studentOpt.get().getId().equals(profileId);
    }

    private UploadFileResult writeFile(MultipartFile file) {
        return storageService.uploadFile(file,
                FilenameUtils.getExtension(file.getOriginalFilename()));

    }
}