package com.dutact.web.features.event.admin.services.post;

import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.core.entities.post.Post;
import com.dutact.web.core.entities.post.PostStatus;
import com.dutact.web.core.repositories.PostRepository;
import com.dutact.web.features.event.admin.dtos.post.PostCreateDto;
import com.dutact.web.features.event.admin.dtos.post.PostDto;
import com.dutact.web.features.event.admin.dtos.post.PostUpdateDto;
import com.dutact.web.features.event.events.PostCreatedEvent;
import com.dutact.web.storage.StorageService;
import com.dutact.web.storage.UploadFileResult;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Optional;

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
    public PostDto createPost(PostCreateDto postDto) {
        Post post = postMapper.toPost(postDto);
        post.setStatus(new PostStatus.Public());

        UploadFileResult uploadedFile = uploadFile(postDto.getCoverPhoto());
        post.setCoverPhoto(uploadedFileMapper.toUploadedFile(uploadedFile));

        postRepository.save(post);

        eventPublisher.publishEvent(new PostCreatedEvent(post.getId()));

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
    public PostDto updatePost(Integer postId, PostUpdateDto postUpdateDto)
            throws NotExistsException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotExistsException("Post not found"));
        postMapper.updatePost(post, postUpdateDto);

        if (postUpdateDto.getCoverPhoto() != null) {
            UploadFileResult uploadFileResult = writeFile(postUpdateDto.getCoverPhoto());
            post.setCoverPhoto(uploadedFileMapper.toUploadedFile(uploadFileResult));
        }


        postRepository.save(post);

        return postMapper.toPostDto(post);
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
