package ru.sberbank.edu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Helper class for deserializing wind field in response json
 */
@Getter
public class Wind {
    @JsonProperty("speed")
    float speed;
    @JsonProperty("deg")
    int deg;
    @JsonProperty("gust")
    float gust;
}
