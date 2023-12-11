package ru.sberbank.edu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Helper class for deserializing main field in response json
 */
@Getter
public class Main {
    @JsonProperty("temp")
    float temp;
    @JsonProperty("feels_like")
    float feels_like;
    @JsonProperty("temp_min")
    float temp_min;
    @JsonProperty("temp_max")
    float temp_max;
    @JsonProperty("pressure")
    int pressure;
    @JsonProperty("humidity")
    int humidity;
}
