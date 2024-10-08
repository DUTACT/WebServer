package com.dutact.web.storage;

import jakarta.annotation.Nullable;

import java.io.InputStream;
import java.net.URL;

public interface StorageService {
    /**
     * @param extension file extension without dot
     */
    UploadFileResult uploadFile(InputStream file, @Nullable String extension);

    void updateFile(String fileId, InputStream file);

    URL getFileUrl(String fileId);
}
