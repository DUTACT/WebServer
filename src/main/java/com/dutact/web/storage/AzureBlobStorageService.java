package com.dutact.web.storage;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.dutact.web.common.utils.MIMEMappingUtils;
import jakarta.annotation.Nullable;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Service
public class AzureBlobStorageService implements StorageService {
    private static final int MAX_GENERATE_RESOURCE_LOCATION_ATTEMPTS = 10;

    private final BlobContainerClient containerClient;

    public AzureBlobStorageService(@Value("${azure.credential.client-id}") String clientId,
                                   @Value("${azure.credential.client-secret}") String clientSecret,
                                   @Value("${azure.profile.tenant-id}") String tenantId,
                                   @Value("${azure.storage.blob.account-name}") String accountName,
                                   @Value("${azure.storage.blob.container-name}") String containerName) {
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(String.format("https://%s.blob.core.windows.net", accountName))
                .credential(clientSecretCredential)
                .buildClient();

        containerClient = blobServiceClient.getBlobContainerClient(containerName);
    }

    @Override
    @SneakyThrows
    public UploadFileResult uploadFile(InputStream file, @Nullable String extension) {
        String resourceLocation = randomAvailableBlobName(extension);
        BlobClient blobClient = containerClient.getBlobClient(resourceLocation);
        InputStream byteStream = new ByteArrayInputStream(file.readAllBytes());

        blobClient.upload(byteStream, true);

        byteStream.reset();
        blobClient.setHttpHeaders(new BlobHttpHeaders()
                .setContentType(new Tika().detect(byteStream)));

        byteStream.close();
        return new UploadFileResult(resourceLocation, new URL(blobClient.getBlobUrl()));
    }

    @SneakyThrows
    @Override
    public UploadFileResult uploadFile(InputStreamSource file, @Nullable String extension) {
        try (InputStream inputStream = file.getInputStream()) {
            return uploadFile(inputStream, extension);
        }
    }

    @Override
    @SneakyThrows
    public void updateFile(String fileId, InputStream file) {
        InputStream byteStream = new ByteArrayInputStream(file.readAllBytes());
        BlobClient blobClient = containerClient.getBlobClient(fileId);

        blobClient.upload(byteStream, true);

        byteStream.reset();
        blobClient.setHttpHeaders(new BlobHttpHeaders()
                .setContentType(new Tika().detect(byteStream)));

        byteStream.close();
    }

    @Override
    public void updateFile(String fileId, InputStreamSource file) {
        try (var inputStream = file.getInputStream()) {
            updateFile(fileId, inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    @SneakyThrows
    public URL getFileUrl(String fileId) {
        return new URL(containerClient.getBlobClient(fileId).getBlobUrl());
    }

    @Override
    public void deleteFile(String fileId) {
        containerClient.getBlobClient(fileId).delete();
    }

    private String randomAvailableBlobName(String extension) {
        for (int i = 0; i < MAX_GENERATE_RESOURCE_LOCATION_ATTEMPTS; i++) {
            String fileName = randomFileName(extension);
            if (!containerClient.getBlobClient(fileName).exists()) {
                return fileName;
            }
        }
        throw new IllegalStateException("Failed to generate available resource location");
    }

    private String randomFileName(String extension) {
        return UUID.randomUUID() + "." + extension;
    }

    private String getContentType(@Nullable String extension) {
        if (extension == null) {
            return "application/octet-stream";
        }
        return MIMEMappingUtils.getMIMEType(extension);
    }
}
