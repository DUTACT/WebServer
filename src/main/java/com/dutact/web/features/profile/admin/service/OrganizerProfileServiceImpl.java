package com.dutact.web.features.profile.admin.service;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.dto.NewPasswordDto;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import com.dutact.web.auth.exception.NoPermissionException;
import com.dutact.web.auth.factors.AccountService;
import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.core.entities.EventOrganizer;
import com.dutact.web.core.repositories.OrganizerRepository;
import com.dutact.web.features.profile.admin.dtos.OrganizerProfileDto;
import com.dutact.web.features.profile.admin.dtos.OrganizerProfileUpdateDto;
import com.dutact.web.storage.StorageService;
import com.dutact.web.storage.UploadFileResult;
import jakarta.annotation.Nonnull;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service("organizerProfileService")
public class OrganizerProfileServiceImpl implements OrganizerProfileService {
    private final OrganizerProfileMapper organizerProfileMapper;
    private final UploadedFileMapper uploadedFileMapper;
    private final OrganizerRepository organizerRepository;
    private final AccountService accountService;
    private final StorageService storageService;

    public OrganizerProfileServiceImpl(OrganizerProfileMapper organizerProfileMapper,
                                       UploadedFileMapper uploadedFileMapper,
                                       OrganizerRepository organizerRepository,
                                       AccountService accountService,
                                       StorageService storageService) {
        this.organizerProfileMapper = organizerProfileMapper;
        this.uploadedFileMapper = uploadedFileMapper;
        this.organizerRepository = organizerRepository;
        this.accountService = accountService;
        this.storageService = storageService;
    }

    @Override
    public OrganizerProfileDto getProfile(Integer id) throws NotExistsException {
        return organizerProfileMapper.toProfileDto(organizerRepository.findById(id).orElseThrow(NotExistsException::new));
    }

    @Override
    public OrganizerProfileDto updateProfile(Integer id, OrganizerProfileUpdateDto organizerProfile) throws NotExistsException, ConflictException {
        EventOrganizer eventOrganizer = organizerRepository.findById(id).orElseThrow(NotExistsException::new);
        if (!isProfileOwner(id)) {
            throw new ConflictException();
        }
        organizerProfileMapper.updateProfile(eventOrganizer, organizerProfile);

        if (organizerProfile.getAvatar() != null) {
            if (eventOrganizer.getAvatar() != null) {
                storageService.deleteFile(eventOrganizer.getAvatar().getFileId());
            }
            
            var uploadedFile = writeFile(organizerProfile.getAvatar());
            eventOrganizer.setAvatar(uploadedFileMapper.toUploadedFile(uploadedFile));
        }

        return organizerProfileMapper.toProfileDto(organizerRepository.save(eventOrganizer));
    }

    @Override
    public void changePassword(Integer id, NewPasswordDto newPasswordDto) throws NotExistsException, InvalidLoginCredentialsException, NoPermissionException {
        EventOrganizer eventOrganizer = organizerRepository.findById(id).orElseThrow(NotExistsException::new);
        accountService.changePassword(id, newPasswordDto);
    }


    private boolean isProfileOwner(Integer profileId) throws NotExistsException {
        String username = SecurityContextUtils.getUsername();

        Optional<EventOrganizer> studentOpt = organizerRepository.findByUsername(username);
        if (studentOpt.isEmpty()) {
            throw new NotExistsException();
        }

        return studentOpt.get().getId().equals(profileId);
    }

    private UploadFileResult writeFile(@Nonnull MultipartFile file) {
        return storageService.uploadFile(file,
                FilenameUtils.getExtension(file.getOriginalFilename()));

    }
}
