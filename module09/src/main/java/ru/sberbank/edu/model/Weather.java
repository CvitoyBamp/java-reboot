package ru.sberbank.edu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Helper class for deserializing weather field in response json
 */
@Getter
public class Weather {

    @JsonProperty("id")
    int id;
    @JsonProperty("main")
    String main;
    @JsonProperty("description")
    String description;
    @JsonProperty("icon")
    String icon;

}
