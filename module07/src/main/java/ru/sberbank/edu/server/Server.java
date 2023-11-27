package ru.sberbank.edu.server;

import ru.sberbank.edu.WeatherCache;
import ru.sberbank.edu.WeatherProvider;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Simple server for testing cache
 */
public class Server {

    // server port
    private final int port;
    WeatherCache weatherCache;
    public Server(int port) throws IOException {
        this.port = port;
        weatherCache = new WeatherCache();
    }

    // start simple server
    public void start() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.printf("Server started on port: %s.\n", port);
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    processInput(socket);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    socket.close();
                }
            }
        }
    }

    // get city name from request path (localhost:8080/London) with adding it to cache;
    private void processInput(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var cityFromRequest = reader.lines().findFirst().orElseThrow().split(" ")[1].replace("/", "");

        System.out.println(weatherCache.getWeatherInfo(cityFromRequest));

        System.out.println(weatherCache.getCache());

        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.write("200");
        writer.flush();
    }


}
