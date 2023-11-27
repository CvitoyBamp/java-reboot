package ru.sberbank.edu;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class WeatherProvider {

    private final String API_KEY = "e7704bc895b4a8d2dfd4a29d404285b6"; //c12406c7187634c7e7c4d4ca178f0a64
    private final String URL = "http://api.openweathermap.org";
    RestTemplate restTemplate;
    public WeatherProvider() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Download ACTUAL weather info from internet.
     * You should call GET http://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
     * You should use Spring Rest Template for calling requests
     *
     * @param city - city
     * @return weather info or null
     */
    public WeatherInfo get(@NotNull String city) {

        // build uri
        URI uri = UriComponentsBuilder.fromUriString(URL)
                .path("/data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .build()
                .toUri();

        try {
            WeatherInfo weatherInfo = restTemplate.getForObject(uri, WeatherInfo.class);
            weatherInfo.setCity(city);
            // set Expire time (adding 5 minutes from now)
            weatherInfo.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            return weatherInfo;
        // should return null if city don't exist
        } catch (HttpClientErrorException.NotFound notFound) {
            return null;
        } catch (HttpClientErrorException clientErrorException) {
            throw new HttpClientErrorException(clientErrorException.getStatusCode(), clientErrorException.getMessage());
        }
    }
}