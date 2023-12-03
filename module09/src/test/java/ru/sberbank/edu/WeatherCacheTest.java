package ru.sberbank.edu;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class WeatherCacheTest {
    @Mock
    private WeatherProvider weatherProvider;
    @InjectMocks
    private WeatherCache weatherCache = new WeatherCache();
    static WeatherInfo msk;
    static WeatherInfo tokio;

    // initialize two cities constructors
    @BeforeAll
    static void setUp() {
        msk = new WeatherInfo("Moscow", "Clear", "clear sky", 265.61, 263.04, 1028.0, 1.37, LocalDateTime.now());
        tokio = new WeatherInfo("Tokio", "Clouds", "few clouds", 282.79, 279.17, 1005.0, 8.75, LocalDateTime.now());
    }

    @Test
    @DisplayName("test getWeatherInfo method for existing city")
    void getWeatherInfoTest() {
        Mockito.when(weatherProvider.get(Mockito.anyString()))
                .thenReturn(msk);

        Assertions.assertThat(weatherCache.getWeatherInfo("Moscow")).isEqualTo(msk);
    }

    @Test
    @DisplayName("test get method for not existing city (should be null)")
    void getWeatherInfoNoCityTest() {
        Mockito.when(weatherProvider.get(Mockito.anyString()))
                .thenReturn(null);

        Assertions.assertThat(weatherCache.getWeatherInfo("Moscow")).isNull();
    }
}
