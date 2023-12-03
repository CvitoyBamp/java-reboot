package ru.sberbank.edu;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@Component
public class WeatherProvider {

    private String API_KEY;
    private final String URL = "http://api.openweathermap.org";
    RestTemplate restTemplate;

    @Autowired
    public WeatherProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${api.key}")
    public void setApiKey(String API_KEY) {
        this.API_KEY = API_KEY;
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