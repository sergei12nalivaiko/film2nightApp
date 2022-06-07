package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.entity.Film;

import java.util.List;

public interface FilmService {

    void saveFilm(Film film);

    void saveAllFilms(List<Film> films);

    void deleteAllFilms();

    Film blockFilmById(long id);

    Film findFilmById(long id);

    List<Film> getAvailableFilms();
}
