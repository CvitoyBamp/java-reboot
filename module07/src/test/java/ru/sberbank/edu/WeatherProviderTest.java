package ru.sberbank.edu;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.invocation.MockitoMethod;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WeatherProviderTest {
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private WeatherProvider weatherProvider = new WeatherProvider();

    static WeatherInfo msk;
    static WeatherInfo tokio;

    // initialize two cities constructors
    @BeforeAll
    static void setUp() {
        msk = new WeatherInfo(null, "Clear", "clear sky", 265.61, 263.04, 1028.0, 1.37, null);
        tokio = new WeatherInfo(null, "Clouds", "few clouds", 282.79, 279.17, 1005.0, 8.75, null);
    }

    @Test
    @DisplayName("Check for correct mapping")
    void mappingTest() throws IOException {
        WeatherInfo weatherInfo = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .readValue(new File("src/test/resources/test.json"), WeatherInfo.class);

        Assertions.assertThat(weatherInfo.getPressure()).isEqualTo(1009);
        Assertions.assertThat(weatherInfo.getShortDescription()).isEqualTo("Rain");
        Assertions.assertThat(weatherInfo.getDescription()).isEqualTo("moderate rain");
        Assertions.assertThat(BigDecimal.valueOf(weatherInfo.getTemperature()).setScale(2, BigDecimal.ROUND_HALF_UP)).isEqualTo(new BigDecimal("284.57"));
        Assertions.assertThat(BigDecimal.valueOf(weatherInfo.getFeelsLikeTemperature()).setScale(2, BigDecimal.ROUND_HALF_UP)).isEqualTo(new BigDecimal("284.17"));
        Assertions.assertThat(BigDecimal.valueOf(weatherInfo.getWindSpeed()).setScale(2, BigDecimal.ROUND_HALF_UP)).isEqualTo(new BigDecimal("4.63"));
    }

    @Test
    @DisplayName("test get method for existing city")
    void getWeatherInfoTest() {
        Mockito.when(restTemplate.getForObject(Mockito.any(), ArgumentMatchers.any()))
                .thenReturn(msk);

        Assertions.assertThat(weatherProvider.get("Moscow").getCity()).isEqualTo("Moscow");
        Assertions.assertThat(weatherProvider.get("Moscow").getTemperature()).isEqualTo(265.61);
    }

    @Test
    @DisplayName("test get method for not existing city (should be null)")
    void getWeatherInfoNoCityTest() {
        Mockito.when(restTemplate.getForObject(Mockito.any(), ArgumentMatchers.any()))
                .thenThrow(HttpClientErrorException.create("City not found", HttpStatusCode.valueOf(404), null, null, null, null));

        Assertions.assertThat(weatherProvider.get("Moscow")).isNull();
    }

    @Test
    @DisplayName("test get method for bad api data (should throw HttpClientErrorException)")
    void getWeatherInfoBadKeyTest() {
        Mockito.when(restTemplate.getForObject(Mockito.any(), ArgumentMatchers.any()))
                .thenThrow(HttpClientErrorException.create("Bad api key", HttpStatusCode.valueOf(401), null, null, null, null));

        Assertions.assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> weatherProvider.get("Moscow"));
    }
}
