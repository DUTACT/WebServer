package com.dutact.web.storage;

import jakarta.annotation.Nullable;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class StorageServiceCodecDecorator implements StorageService {
    private static final Charset BASE64_ENCODING_CHARSET = StandardCharsets.ISO_8859_1;

    private final AzureBlobStorageService storageService;

    public StorageServiceCodecDecorator(AzureBlobStorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public UploadFileResult uploadFile(InputStream file, @Nullable String extension) {
        UploadFileResult uploadFileResult = storageService.uploadFile(file, extension);
        String encodedFileId = encode(uploadFileResult.fileId());

        return new UploadFileResult(encodedFileId, uploadFileResult.fileUrl());
    }

    @Override
    public URL getFileUrl(String fileId) {
        String decodedFileId = decode(fileId);

        return storageService.getFileUrl(decodedFileId);
    }

    private String encode(String str) {
        byte[] encodedBytes = Base64.getEncoder().encode(str.getBytes());
        return new String(encodedBytes, BASE64_ENCODING_CHARSET);
    }

    private String decode(String str) {
        byte[] decodedBytes = Base64.getDecoder().decode(str.getBytes(BASE64_ENCODING_CHARSET));
        return new String(decodedBytes);
    }
}
