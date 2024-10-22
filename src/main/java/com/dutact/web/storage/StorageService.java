package com.dutact.web.storage;

import jakarta.annotation.Nullable;
import org.springframework.core.io.InputStreamSource;

import java.io.InputStream;
import java.net.URL;

public interface StorageService {
    /**
     * @param extension file extension without dot
     */
    UploadFileResult uploadFile(InputStream file, @Nullable String extension);

    /**
     * @param extension file extension without dot
     */
    UploadFileResult uploadFile(InputStreamSource file, @Nullable String extension);

    /**
     * Update file content, if file does not exist, create a new file with the given id then update its content
     */
    void updateFile(String fileId, InputStream file);

    /**
     * Update file content, if file does not exist, create a new file with the given id then update its content
     */
    void updateFile(String fileId, InputStreamSource file);

    URL getFileUrl(String fileId);

    void deleteFile(String fileId);
}
