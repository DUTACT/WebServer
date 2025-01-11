package com.dutact.web.data.entity.checkincode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CheckInLocation {
    @JsonProperty("title")
    private String title;

    @JsonProperty("geoPosition")
    private GeoPosition geoPosition;
}
