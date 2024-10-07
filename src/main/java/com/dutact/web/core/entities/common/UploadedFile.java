package com.dutact.web.core.entities.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadedFile {
    @JsonProperty("fileIdentifier")
    private String fileIdentifier;

    @JsonProperty("fileUrl")
    private String fileUrl;
}
