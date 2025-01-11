package com.dutact.web.service.profile.student;

import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.auth.SecurityContextUtils;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.common.storage.StorageService;
import com.dutact.web.common.storage.UploadFileResult;
import com.dutact.web.data.entity.Student;
import com.dutact.web.data.repository.StudentRepository;
import com.dutact.web.dto.auth.NewPasswordDto;
import com.dutact.web.dto.profile.student.StudentProfileDto;
import com.dutact.web.dto.profile.student.StudentProfileUpdateDto;
import com.dutact.web.service.auth.AccountService;
import com.dutact.web.service.auth.exception.InvalidCredentialsException;
import com.dutact.web.service.auth.exception.NoPermissionException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service("studentProfileService")
public class StudentProfileServiceImpl implements StudentProfileService {
    private final StudentProfileMapper studentProfileMapper;
    private final UploadedFileMapper uploadedFileMapper;
    private final StudentRepository studentRepository;
    private final StorageService storageService;
    private final AccountService accountService;

    public StudentProfileServiceImpl(StudentProfileMapper studentProfileMapper,
                                     UploadedFileMapper uploadedFileMapper,
                                     StudentRepository studentRepository,
                                     StorageService storageService,
                                     AccountService accountService
    ) {
        this.studentProfileMapper = studentProfileMapper;
        this.uploadedFileMapper = uploadedFileMapper;
        this.studentRepository = studentRepository;
        this.storageService = storageService;
        this.accountService = accountService;
    }

    @Override
    public StudentProfileDto getProfile(Integer studentId) throws NotExistsException {
        return studentProfileMapper.toProfileDto(studentRepository.findById(studentId).orElseThrow(NotExistsException::new));
    }

    @Override
    public StudentProfileDto updateProfile(Integer studentId, StudentProfileUpdateDto studentProfileDto) throws NotExistsException, ConflictException {
        Student student = studentRepository.findById(studentId).orElseThrow(NotExistsException::new);
        if (!isProfileOwner(studentId)) {
            throw new ConflictException();
        }
        studentProfileMapper.updateProfile(student, studentProfileDto);
        if (studentProfileDto.getAvatar() != null) {
            UploadFileResult uploadFileResult = writeFile(studentProfileDto.getAvatar());
            student.setAvatar(uploadedFileMapper.toUploadedFile(uploadFileResult));
        }

        return studentProfileMapper.toProfileDto(studentRepository.save(student));
    }

    @Override
    public void changePassword(Integer studentId, NewPasswordDto newPasswordDto) throws NotExistsException, NoPermissionException, InvalidCredentialsException {
        Student student = studentRepository.findById(studentId).orElseThrow(NotExistsException::new);

        accountService.changePassword(studentId, newPasswordDto);
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
