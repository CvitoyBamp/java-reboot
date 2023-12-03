package ru.sberbank.edu;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class WeatherCache {
    private final Map<String, WeatherInfo> cache = new HashMap<>();

    @Autowired
    WeatherProvider weatherProvider;

    /**
     * Get ACTUAL weather info for current city or null if current city not found.
     * If cache doesn't contain weather info OR contains NOT ACTUAL info then we should download info
     * If you download weather info then you should set expiry time now() plus 5 minutes.
     * If you can't download weather info then remove weather info for current city from cache.
     *
     * @param city - city
     * @return actual weather info
     */
    public WeatherInfo getWeatherInfo(@NotNull String city) {

        synchronized (cache) {
            try {
                WeatherInfo weatherInfo = weatherProvider.get(city);
                if (weatherInfo != null) {
                    cache.entrySet().stream().filter(val -> val.getKey().equals(city))
                            .findAny()
                            .map(Map.Entry::getValue)
                            .ifPresentOrElse(
                                    (wi) -> {
                                        if (LocalDateTime.now().isAfter(wi.getExpiryTime())) {
                                                cache.replace(city, weatherInfo);
                                                System.out.println("Weather info for city " + city + " was updated.");
                                        }
                                    },
                                    () -> {
                                        cache.put(city, weatherInfo);
                                        System.out.println("City " + city + " was added to cache.");
                                    }
                            );
                }
                return weatherInfo;
            } catch (HttpClientErrorException ex) {
                removeWeatherInfo(city);
                return null;
            }
        }
    }

    /**
     * Remove weather info from cache.
     **/
    public void removeWeatherInfo(String city) {
        synchronized (cache) {
            cache.remove(city);
        }
    }

}
