package ru.sberbank.edu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Travel Service.
 */
public class TravelService {

    // do not change type
    private final List<CityInfo> cities = new ArrayList<>();

    /**
     * Append city info.
     * @param cityInfo - city info
     * @throws IllegalArgumentException if city already exists
     */

    public void add(CityInfo cityInfo) {
        cities.stream().filter(cityInfo::equals).findAny().ifPresentOrElse(
                (ci) -> {
                    throw new IllegalArgumentException(String.format("City %s is already exist.", cityInfo.getName()));
                },
                () -> {
                    cities.add(cityInfo);
                });
    }

    /**
     * remove city info.
     * @param cityName - city name
     * @throws IllegalArgumentException if city doesn't exist
     */
    public void remove(String cityName) {
        cities.stream().filter(ci -> (ci.getName().equals(cityName))).findFirst().ifPresentOrElse(
                cities::remove,
                () -> {
                    throw new IllegalArgumentException("City doesn't exist.");
                }
        );
    }

    /**
     * Get cities names.
     */
    public List<String> citiesNames() {
        return cities.stream().map(CityInfo::getName).collect(Collectors.toList());
    }

    /**
     * Get distance in kilometers between two cities.
     * https://www.kobzarev.com/programming/calculation-of-distances-between-cities-on-their-coordinates/
     *
     * @param srcCityName  - source city
     * @param destCityName - destination city
     * @throws IllegalArgumentException if source or destination city doesn't exist.
     */
    public int getDistance(String srcCityName, String destCityName) {
        List<CityInfo> cis = cities.stream().filter(ci -> ci.getName().equals(srcCityName) || ci.getName().equals(destCityName)).toList();
        if (cis.size() == 2) {
            return calculateDistance(cis.get(0).getPosition(), cis.get(1).getPosition());
        } else if (cis.size() == 1) {
            return 0; // тот же город
        } else {
            throw new IllegalArgumentException("One of the cities doesn't exist.");
        }
    }

    /**
     * Get all cities near current city in radius.
     *
     * @param cityName - city
     * @param radius   - radius in kilometers for search
     * @throws IllegalArgumentException if city with cityName city doesn't exist.
     */
    public List<String> getCitiesNear(String cityName, int radius) {
        return cities.stream().map(CityInfo::getName)
                .filter(cn -> !cn.equals(cityName))
                .filter(cn -> getDistance(cn, cityName) <= radius).toList();
    }

    /**
     * Calculate the distance between two cities
     * @param firstCity geo position of first city
     * @param secondCity geo position of second city
     * @return distance between two cities in meters
     */
    private int calculateDistance(GeoPosition firstCity, GeoPosition secondCity) {

        final int EARTH_RADIUS = 6372795;

        final double cosLatFirstCity = Math.cos(firstCity.getLatitude());
        final double sinLatFirstCity = Math.sin(firstCity.getLatitude());
        final double cosLatSecondCity = Math.cos(secondCity.getLatitude());
        final double sinLatSecondCity = Math.sin(secondCity.getLatitude());
        final double longDelta = secondCity.getLongitude() - firstCity.getLongitude();
        final double cosDelta = Math.cos(longDelta);
        final double sinDelta = Math.sin(longDelta);

        final double y = Math.sqrt(Math.pow(cosLatSecondCity*sinDelta, 2) +
                Math.pow(cosLatFirstCity*sinLatSecondCity-sinLatFirstCity*cosLatSecondCity*cosDelta, 2));

        final double x = sinLatFirstCity*sinLatSecondCity + cosLatFirstCity*cosLatSecondCity*cosDelta;

        final double answer = Math.atan2(y, x)*EARTH_RADIUS;

        return (int) answer;
    }

}
