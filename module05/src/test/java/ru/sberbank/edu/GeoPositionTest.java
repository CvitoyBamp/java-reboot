package ru.sberbank.edu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static ru.sberbank.edu.GeoPosition.parseByRegex;

public class GeoPositionTest {

    @Test
    @DisplayName("Check parseByRegex func with correct pattern")
    void parseByRegexTestCorrectValues() {
        Assertions.assertThat(parseByRegex(String.valueOf(55))).isEqualTo(0.9599310885968813);
    }

    @Test
    @DisplayName("Check parseByRegex func with invalid pattern")
    void parseByRegexTestInvalidPattern() {
        // нет минутного штриха
        Assertions.assertThatIllegalStateException().isThrownBy(() -> parseByRegex("58(4707'')"));
        // нет секундного штриха
        Assertions.assertThatExceptionOfType(NumberFormatException.class).isThrownBy(() -> parseByRegex("58(47'07)"));
        // нет скобки в конце
        Assertions.assertThatIllegalStateException().isThrownBy(() -> parseByRegex("58(47'07''"));
    }

    @Test
    @DisplayName("Check GeoPosition constructor")
    void geoPositionConstructorTest() {
        GeoPosition geoPosition = new GeoPosition("55", "58(47'07'')");
        Assertions.assertThat(geoPosition.getLatitude()).isEqualTo(0.9599310885968813);
        Assertions.assertThat(geoPosition.getLongitude()).isEqualTo(1.0259966489216779);
    }
}
