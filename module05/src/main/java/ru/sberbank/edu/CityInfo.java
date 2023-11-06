package ru.sberbank.edu;

import java.util.Objects;

/**
 * City info
 */
public class CityInfo {

    private String name;
    private GeoPosition position;

    /**
     * Constructor
     * @param name     - city name
     * @param position - position
     */
    public CityInfo(String name, GeoPosition position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public GeoPosition getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof CityInfo cityInfo)) return false;
        return Objects.equals(getName(), cityInfo.getName()) && Objects.equals(getPosition(), cityInfo.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPosition());
    }
}