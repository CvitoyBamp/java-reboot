package ru.sberbank.edu;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Geo position.
 */
public class GeoPosition {

    /**
     * Широта в радианах.
     */
    private double latitude;

    /**
     * Долгота в радианах.
     */
    private double longitude;

    /**
     * Ctor.
     *
     * @param latitudeGradus  - latitude in gradus
     * @param longitudeGradus - longitude in gradus
     *                        Possible values: 55, 55(45'07''), 59(57'00'')
     */
    public GeoPosition(String latitudeGradus, String longitudeGradus){
            this.latitude = parseByRegex(latitudeGradus);
            this.longitude = parseByRegex(longitudeGradus);
    }

    /**
     * Convert gradus with minutes/seconts to radians
     * @param value - input latitude or longitude in gradus
     * @return value in radians
     */
    protected static Double parseByRegex(String value) {
        try {
            return Double.parseDouble(value)*Math.PI/180;
        } catch (NumberFormatException nfe) {
            Pattern pattern = Pattern.compile("(.*?)\\((.*?)'(.*).{2}\\)");
            Matcher matcher = pattern.matcher(value);
            if (matcher.matches()) {
                try {
                    return (Double.parseDouble(matcher.group(1)) +
                            Double.parseDouble(matcher.group(2)) / 60 +
                            Double.parseDouble(matcher.group(3)) / 3600) * Math.PI / 180;
                } catch (NumberFormatException nfeInternal) {
                    throw new NumberFormatException(nfeInternal.getMessage());
                }
            } else {
                throw new IllegalStateException("No pattern found");
            }
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Координаты: " +
                "Широта=" + latitude +
                ", Долгота=" + longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof GeoPosition that)) return false;
        return Double.compare(that.getLatitude(), getLatitude()) == 0 && Double.compare(that.getLongitude(), getLongitude()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude());
    }
}