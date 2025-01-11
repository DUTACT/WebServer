package com.dutact.web.service.post.admin;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.common.storage.StorageService;
import com.dutact.web.common.storage.UploadFileResult;
import com.dutact.web.data.entity.common.UploadedFile;
import com.dutact.web.data.entity.post.Post;
import com.dutact.web.data.entity.post.PostStatus;
import com.dutact.web.data.repository.PostRepository;
import com.dutact.web.dto.post.admin.*;
import com.dutact.web.event.event.PostCreatedEvent;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Log4j2
@Service("adminPostService")
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final UploadedFileMapper uploadedFileMapper;
    private final PostRepository postRepository;
    private final StorageService storageService;
    private final ApplicationEventPublisher eventPublisher;

    public PostServiceImpl(PostMapper postMapper,
                           UploadedFileMapper uploadedFileMapper,
                           PostRepository postRepository,
                           StorageService storageService,
                           ApplicationEventPublisher eventPublisher) {
        this.postMapper = postMapper;
        this.uploadedFileMapper = uploadedFileMapper;
        this.postRepository = postRepository;
        this.storageService = storageService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public PostDto createPost(PostCreateDtoV2 postDto) {
        Post post = postMapper.toPost(postDto);
        post.setStatus(new PostStatus.Public());

        if (postDto.getCoverPhotos() == null) {
            postDto.setCoverPhotos(new ArrayList<>());
        }
        for (MultipartFile coverPhoto : postDto.getCoverPhotos()) {
            UploadFileResult uploadedFile = uploadFile(coverPhoto);
            post.getCoverPhotos().add(uploadedFileMapper.toUploadedFile(uploadedFile));
        }

        postRepository.save(post);

        try {
            eventPublisher.publishEvent(new PostCreatedEvent(post.getId()));
        } catch (Exception e) {
            log.error("Failed to publish PostCreatedEvent", e);
        }

        return postMapper.toPostDto(post);
    }

    @Override
    public PostDto createPost(PostCreateDtoV1 postDto) {
        Post post = postMapper.toPost(postDto);
        post.setStatus(new PostStatus.Public());

        UploadFileResult uploadedFile = uploadFile(postDto.getCoverPhoto());
        post.getCoverPhotos().add(uploadedFileMapper.toUploadedFile(uploadedFile));

        postRepository.save(post);

        try {
            eventPublisher.publishEvent(new PostCreatedEvent(post.getId()));
        } catch (Exception e) {
            log.error("Failed to publish PostCreatedEvent", e);
        }
        return postMapper.toPostDto(post);
    }

    @Override
    public Collection<PostDto> getPosts(Integer eventId) {

        return postRepository.findAllByEventId(eventId).stream().map(postMapper::toPostDto).toList();
    }

    @Override
    public Optional<PostDto> getPost(Integer postId) {
        return postRepository.findById(postId).map(postMapper::toPostDto);
    }

    @Override
    @SneakyThrows
    public PostDto updatePost(Integer postId, PostUpdateDtoV2 postUpdateDtoV2)
            throws NotExistsException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotExistsException("Post not found"));
        postMapper.updatePost(post, postUpdateDtoV2);

        updateCoverPhoto(post, postUpdateDtoV2);

        postRepository.save(post);

        return postMapper.toPostDto(post);
    }

    @Override
    @SneakyThrows
    public PostDto updatePost(Integer postId, PostUpdateDtoV1 postUpdateDtoV1) throws NotExistsException {
        Post post = postRepository.findById(postId).orElseThrow();
        postMapper.updatePost(post, postUpdateDtoV1);
        updateCoverPhoto(post, postUpdateDtoV1);
        postRepository.save(post);
        return postMapper.toPostDto(post);
    }

    void updateCoverPhoto(Post post, PostUpdateDtoV2 postUpdateDtoV2) {
        var len = post.getCoverPhotos().size();
        var urlsNeedToDelete = new ArrayList<String>();
        for (int i = 0; i < len; i++) {
            if (!postUpdateDtoV2.getKeepCoverPhotoUrls().contains(post.getCoverPhotos().get(i).getFileUrl())) {
                urlsNeedToDelete.add(post.getCoverPhotos().get(i).getFileUrl());
            }
        }
        for (String url : urlsNeedToDelete) {
            for (UploadedFile coverPhoto : post.getCoverPhotos()) {
                storageService.deleteFile(coverPhoto.getFileId());
                if (coverPhoto.getFileUrl().equals(url)) {
                    post.getCoverPhotos().remove(coverPhoto);
                    break;
                }
            }
        }
        if (postUpdateDtoV2.getCoverPhotos() == null) {
            postUpdateDtoV2.setCoverPhotos(new ArrayList<>());
        }
        for (MultipartFile coverPhoto : postUpdateDtoV2.getCoverPhotos()) {
            var uploadFileResult = storageService.uploadFile(coverPhoto, FilenameUtils.getExtension(coverPhoto.getOriginalFilename()));
            post.getCoverPhotos().add(uploadedFileMapper.toUploadedFile(uploadFileResult));
        }
    }

    void updateCoverPhoto(Post post, PostUpdateDtoV1 postUpdateDtoV1) {
        post.setCoverPhotos(new ArrayList<>());
        if (postUpdateDtoV1.getCoverPhoto() != null) {
            var uploadFileResult = storageService.uploadFile(postUpdateDtoV1.getCoverPhoto(), FilenameUtils.getExtension(postUpdateDtoV1.getCoverPhoto().getOriginalFilename()));
            post.getCoverPhotos().add(uploadedFileMapper.toUploadedFile(uploadFileResult));
        }
    }

    @Override
    public PostStatus updatePostStatus(Integer postId, PostStatus postStatus) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setStatus(postStatus);
        postRepository.save(post);

        return postStatus;
    }

    @Override
    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public boolean postExists(Integer eventId, Integer postId) {
        return postRepository.existsByIdAndEventId(eventId, postId);
    }

    private UploadFileResult uploadFile(MultipartFile file) {
        try (var inputStream = file.getInputStream()) {
            return storageService.uploadFile(inputStream,
                    FilenameUtils.getExtension(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private UploadFileResult writeFile(MultipartFile file) {
        try (var inputStream = file.getInputStream()) {
            return storageService.uploadFile(inputStream,
                    FilenameUtils.getExtension(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
