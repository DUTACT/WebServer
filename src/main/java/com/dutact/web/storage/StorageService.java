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

    void updateFile(String fileId, InputStream file);

    void updateFile(String fileId, InputStreamSource file);

    URL getFileUrl(String fileId);
}
