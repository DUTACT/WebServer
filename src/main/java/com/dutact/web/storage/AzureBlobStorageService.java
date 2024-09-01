package com.dutact.web.storage;

import jakarta.annotation.Nullable;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;

@Service
public class AzureBlobStorageService implements StorageService {
    private static final int MAX_GENERATE_RESOURCE_LOCATION_ATTEMPTS = 10;

    private final ResourceLoader resourceLoader;
    private final String containerName;

    public AzureBlobStorageService(@Qualifier("azureStorageBlobProtocolResolver") ResourceLoader resourceLoader,
                                   @Value("${dutact.storage.azure.blob.container-name}") String containerName) {
        this.resourceLoader = resourceLoader;
        this.containerName = containerName;
    }

    @Override
    @SneakyThrows
    public UploadFileResult uploadFile(InputStream file, @Nullable String extension) {
        String resourceLocation = randomAvailableResourceLocation(extension);
        WritableResource resource = (WritableResource) resourceLoader.getResource(resourceLocation);

        try (OutputStream outputStream = resource.getOutputStream()) {
            file.transferTo(outputStream);
        }

        return new UploadFileResult(resourceLocation, resource.getURL());
    }


    @Override
    @SneakyThrows
    public URL getFileUrl(String fileId) {
        return resourceLoader.getResource(fileId).getURL();
    }

    private String randomAvailableResourceLocation(String extension) {
        for (int i = 0; i < MAX_GENERATE_RESOURCE_LOCATION_ATTEMPTS; i++) {
            String fileName = randomFileName(extension);
            String resourceLocation = getResourceLocation(fileName);
            if (!resourceLoader.getResource(resourceLocation).exists()) {
                return resourceLocation;
            }
        }
        throw new IllegalStateException("Failed to generate available resource location");
    }

    private String randomFileName(String extension) {
        return UUID.randomUUID() + "." + extension;
    }

    private String getResourceLocation(String fileName) {
        return "azure-blob://" + containerName +
                "/" +
                fileName;
    }
}
