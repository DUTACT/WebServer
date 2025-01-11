package com.dutact.web.data.entity.checkincode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeoPosition {
    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lng")
    private Double lng;
}
