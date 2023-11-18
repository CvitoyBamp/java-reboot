package ru.sberbank.edu;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        TravelService cityInfoList = new TravelService();
        cityInfoList.add(new CityInfo("Moscow", new GeoPosition("55(45'7'')", "37(36'56'')")));
        cityInfoList.add(new CityInfo("Omsk", new GeoPosition("54(59'18'')", "73(19'27'')")));
        cityInfoList.add(new CityInfo("Habarovsk", new GeoPosition("48(30'57'')", "135(6'4'')")));
        cityInfoList.add(new CityInfo("Paris", new GeoPosition("48(51'24'')", "2(21'8'')")));
        cityInfoList.add(new CityInfo("Lissabon", new GeoPosition("38(43'20'')", "-9(8'22'')")));
        cityInfoList.add(new CityInfo("NewYork", new GeoPosition("40(42'46'')", "-74(0'21'')")));
        cityInfoList.add(new CityInfo("Toronto", new GeoPosition("43(39'11'')", "-79(22'59'')")));

        System.out.println(cityInfoList.getDistance("Moscow", "Toronto"));

    }
}
