package com.dutact.web.features.event.admin.services.post;

import com.dutact.web.common.mapper.UploadedFileMapper;
import com.dutact.web.core.entities.post.Post;
import com.dutact.web.core.entities.post.PostStatus;
import com.dutact.web.core.repositories.PostRepository;
import com.dutact.web.features.event.admin.dtos.post.PostCreateDto;
import com.dutact.web.features.event.admin.dtos.post.PostDto;
import com.dutact.web.storage.StorageService;
import com.dutact.web.storage.UploadFileResult;
import org.apache.commons.io.FilenameUtils;
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

    public PostServiceImpl(PostMapper postMapper,
                           UploadedFileMapper uploadedFileMapper,
                           PostRepository postRepository,
                           StorageService storageService) {
        this.postMapper = postMapper;
        this.uploadedFileMapper = uploadedFileMapper;
        this.postRepository = postRepository;
        this.storageService = storageService;
    }

    @Override
    public PostDto createPost(PostCreateDto postDto) {
        Post post = postMapper.toPost(postDto);
        post.setStatus(new PostStatus.Public());

        UploadFileResult uploadedFile = uploadFile(postDto.getCoverPhoto());
        post.setCoverPhoto(uploadedFileMapper.toUploadedFile(uploadedFile));

        return postMapper.toPostDto(postRepository.save(post));
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
}
