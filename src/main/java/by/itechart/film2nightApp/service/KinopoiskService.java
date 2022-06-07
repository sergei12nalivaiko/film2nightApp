package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.entity.Film;

import java.net.HttpURLConnection;
import java.util.List;

public interface KinopoiskService {

    HttpURLConnection connectToDockerApi(String path);

    StringBuffer readResponse(HttpURLConnection connection);

    List<Film> parseFilmToEntity(String responseBody);

}
