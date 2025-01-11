package com.dutact.web.common.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageServiceConfiguration {
    @Bean
    public StorageService storageService(AzureBlobStorageService azureBlobStorageService) {
        return new StorageServiceCodecDecorator(azureBlobStorageService);
    }
}
