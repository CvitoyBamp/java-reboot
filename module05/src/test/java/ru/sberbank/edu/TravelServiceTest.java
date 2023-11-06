package ru.sberbank.edu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static ru.sberbank.edu.GeoPosition.parseByRegex;

public class TravelServiceTest {
    TravelService travelService = new TravelService();

    @BeforeEach
    void initList() {
        travelService.citiesNames().forEach(System.out::println);
        travelService.add(new CityInfo("Moscow", new GeoPosition("55(45'7'')", "37(36'56'')")));
        travelService.add(new CityInfo("Omsk", new GeoPosition("54(59'18'')", "73(19'27'')")));
        travelService.add(new CityInfo("Habarovsk", new GeoPosition("48(30'57'')", "135(6'4'')")));
        travelService.add(new CityInfo("Paris", new GeoPosition("48(51'24'')", "2(21'8'')")));
        travelService.add(new CityInfo("Lissabon", new GeoPosition("38(43'20'')", "-9(8'22'')")));
        travelService.add(new CityInfo("NewYork", new GeoPosition("40(42'46'')", "-74(0'21'')")));
        travelService.add(new CityInfo("Toronto", new GeoPosition("43(39'11'')", "-79(22'59'')")));
    }

    @AfterEach
    void clearList() {
        for (String cn: travelService.citiesNames()) {
            travelService.remove(cn);
        }
    }

    @Test
    @DisplayName("Add new city info with correct info")
    void addNewCityInfo() {
        travelService.add(new CityInfo("Bishkek", new GeoPosition("45(52'29'')", "74(34'11'')")));
        Assertions.assertThat(travelService.citiesNames().get(travelService.citiesNames().size()-1))
                .isEqualTo("Bishkek");
    }

    @Test
    @DisplayName("Add new city info with duplicate info")
    void addNewCityInfoDuplicate() {
        travelService.add(new CityInfo("Bishkek", new GeoPosition("45(52'29'')", "74(34'11'')")));
        Assertions.assertThatIllegalArgumentException().isThrownBy(() ->
                travelService.add(new CityInfo("Bishkek", new GeoPosition("45(52'29'')", "74(34'11'')"))));
    }

    @Test
    @DisplayName("Remove existing city")
    void removeExistingCity() {
        travelService.remove("Moscow");
        Assertions.assertThat(travelService.citiesNames().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("Try to remove city, which don't exist in list")
    void removeUnExistingCity() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() ->
                travelService.remove("Blablabla"));
    }

    @Test
    @DisplayName("Get cities names.")
    void getCitiesNames() {
        Assertions.assertThat(travelService.citiesNames().size()).isEqualTo(7);
        Assertions.assertThat(travelService.citiesNames())
                .isEqualTo(List.of("Moscow", "Omsk", "Habarovsk", "Paris", "Lissabon", "NewYork", "Toronto"));
    }

    @Test
    @DisplayName("Get distance between cities.")
    void getDistance() {
        List<String> cities = travelService.citiesNames();
        // Москва - Омск
        Assertions.assertThat(travelService.getDistance(cities.get(0), cities.get(1))).isEqualTo(2233562);
        // Москва - Торонто
        Assertions.assertThat(travelService.getDistance(cities.get(0), cities.get(6))).isEqualTo(7452950);
        // Москва - Париж
        Assertions.assertThat(travelService.getDistance(cities.get(0), cities.get(3))).isEqualTo(2486811);
    }

    @Test
    @DisplayName("Get the nearest cities.")
    void getNearCities() {
        // Москва - Омск, Париж
        Assertions.assertThat(travelService.getCitiesNear("Moscow", 2500000)).isEqualTo(List.of("Omsk", "Paris"));
    }

}
