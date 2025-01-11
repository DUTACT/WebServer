package com.dutact.web.data.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFile {
    @JsonProperty("fileId")
    private String fileId;

    @JsonProperty("fileUrl")
    private String fileUrl;
}
