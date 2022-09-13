package by.itechart.film2nightApp.controller;

import by.itechart.film2nightApp.entity.Film;
import by.itechart.film2nightApp.service.FilmService;
import by.itechart.film2nightApp.service.KinopoiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class FilmController {

    private final FilmService filmService;
    private final KinopoiskService kinopoiskService;

    @Autowired
    public FilmController(FilmService filmService, KinopoiskService kinopoiskService) {
        this.filmService = filmService;
        this.kinopoiskService = kinopoiskService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/films")
    public ResponseEntity<StringBuffer> getFilms() {
        filmService.deleteAllFilms();
        HttpURLConnection httpURLConnection = kinopoiskService.connectToDockerApi("http://localhost:8081/api/v.2.2/films/all");
        StringBuffer stringBuffer = kinopoiskService.readResponse(httpURLConnection);
        List<Film> films = kinopoiskService.parseFilmToEntity(stringBuffer.toString());
        filmService.saveAllFilms(films);
        return ResponseEntity.ok().body(stringBuffer);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/available/films")
    public ResponseEntity<List<Film>> getAvailableFilms() {
        return ResponseEntity.ok().body(filmService.getAvailableFilms());
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @PostMapping("/film/block/{id}")
    public ResponseEntity<Film> blockFilmById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok().body(filmService.blockFilmById(id));
    }
}
