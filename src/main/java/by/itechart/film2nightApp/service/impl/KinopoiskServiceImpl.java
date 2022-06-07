package by.itechart.film2nightApp.service.impl;

import by.itechart.film2nightApp.entity.Film;
import by.itechart.film2nightApp.entity.FilmBuilder;
import by.itechart.film2nightApp.service.KinopoiskService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class KinopoiskServiceImpl implements KinopoiskService {

    private HttpURLConnection connection;

    @Override
    public HttpURLConnection connectToDockerApi(String path) {
        try {
            log.info(path);
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Content-Type", "application/json");

            int status = connection.getResponseCode();

            if (status < 299) {
                log.info("Connect successfull");
                return connection;
            } else {
                log.info("Connect is not successful");
            }

        } catch (IOException e) {
            log.error("Failed to connect to kinopoisk api");
        }
        return null;
    }

    @Override
    public StringBuffer readResponse(HttpURLConnection connection) {
        StringBuffer responseContent = new StringBuffer();
        BufferedReader reader;
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
                log.info(responseContent.toString());
            }
        } catch (IOException e) {
            log.error("Failed to read response");
        }
        return responseContent;
    }

    @Override
    public List<Film> parseFilmToEntity(String responseBody) {
        List<Film> films = new ArrayList<>();
        log.info("parseFilmToEntity");
        log.info("in parse");
        JSONObject jsonObject1 = new JSONObject(responseBody);
        JSONArray rows = jsonObject1.getJSONArray("films");
        for (int i = 0; i < rows.length(); i++) {
            JSONObject jsonObject = rows.getJSONObject(i);
            Film film = new FilmBuilder()
                    .withKinopoiskId(jsonObject.getLong("kinopoiskId"))
                    .withFilmLength(jsonObject.getInt("filmLength"))
                    .withRatingKinopoisk(jsonObject.getFloat("ratingKinopoisk"))
                    .withIsBlocked(jsonObject.getBoolean("isBlocked"))
                    .withLastSync(jsonObject.getString("lastSync"))
                    .withNameOrigin(jsonObject.getString("nameOriginal"))
                    .withPosterUrl(jsonObject.getString("posterUrl"))
                    .withYear(jsonObject.getInt("year"))
                    .withRatingKinopoiskVoteCount(jsonObject.getInt("ratingKinopoiskVoteCount"))
                    .withWebUrl(jsonObject.getString("webUrl"))
                    .build();
            films.add(film);
            log.info(String.valueOf(film));
        }
        return films;
    }
}
