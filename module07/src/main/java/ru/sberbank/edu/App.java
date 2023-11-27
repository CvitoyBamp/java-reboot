package ru.sberbank.edu;

import ru.sberbank.edu.server.Server;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Server server = new Server(8080);
        server.start();

//        WeatherCache weatherCache = new WeatherCache();
//
//        Callable<WeatherInfo> taskAdd = () -> weatherCache.getWeatherInfo("Moscow");
//        Runnable taskDelete = () -> weatherCache.removeWeatherInfo("Moscow");
//        ExecutorService service = Executors.newFixedThreadPool(1000);
//        for (int i = 0; i < 100; i++) {
//            service.submit(taskAdd);
//            service.submit(taskDelete);
//        }
    }
}
