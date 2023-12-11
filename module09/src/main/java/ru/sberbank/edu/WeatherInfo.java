package ru.sberbank.edu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.edu.model.Main;
import ru.sberbank.edu.model.Weather;
import ru.sberbank.edu.model.Wind;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfo {

    @JsonIgnore
    private String city;

    /**
     * Short weather description
     * Like 'sunny', 'clouds', 'raining', etc
     */
    private String shortDescription;

    /**
     * Weather description.
     * Like 'broken clouds', 'heavy raining', etc
     */
    private String description;

    /**
     * Unpack necessary values (shortDescription and description) from response Json
     * @param weather - list of weather field in response json
     */
    @JsonProperty("weather")
    private void unpackDescriptions(List<Weather> weather) {
        this.shortDescription = weather.get(0).getMain();
        this.description = weather.get(0).getDescription();
    }

    /**
     * Temperature.
     */
    private double temperature;

    /**
     * Temperature that fells like.
     */
    private double feelsLikeTemperature;

    /**
     * Pressure.
     */
    private double pressure;

    /**
     * Unpack necessary values (temperature, feelsLikeTemperature and pressure) from response Json
     * @param main - main field in response json
     */
    @JsonProperty("main")
    private void unpackTemp(Main main) {
        this.temperature = main.getTemp();
        this.feelsLikeTemperature = main.getFeels_like();
        this.pressure = main.getPressure();
    }

    /**
     * Wind speed.
     */
    private double windSpeed;

    /**
     * Unpack necessary value windSpeed from response Json
     * @param wind - wind field in response json
     */
    @JsonProperty("wind")
    private void unpackWind(Wind wind) {
        this.windSpeed = wind.getSpeed();
    }

    /**
     * Expiry time of weather info.
     * If current time is above expiry time then current weather info is not actual!
     */
    @JsonIgnore
    private LocalDateTime expiryTime;

}
