package ru.sberbank.edu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ru.sberbank.edu.GeoPosition.parseByRegex;

public class CityInfoTest {

    @Test
    @DisplayName("Check CityInfo constructor")
    void cityInfoConstructorTest() {
        GeoPosition geoPosition = new GeoPosition("55", "58(47'07'')");
        CityInfo cityInfo = new CityInfo("Зазеркалье", geoPosition);
        Assertions.assertThat(cityInfo.getName()).isEqualTo("Зазеркалье");
        Assertions.assertThat(cityInfo.getPosition().toString()).isEqualTo("Координаты: Широта=0.9599310885968813, Долгота=1.0259966489216779");
    }
}
