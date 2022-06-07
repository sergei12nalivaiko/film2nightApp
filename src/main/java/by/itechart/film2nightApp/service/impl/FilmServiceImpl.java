package by.itechart.film2nightApp.service.impl;

import by.itechart.film2nightApp.entity.Film;
import by.itechart.film2nightApp.repository.FilmRepository;
import by.itechart.film2nightApp.service.FilmService;
import by.itechart.film2nightApp.specification.FilmSpecification;
import by.itechart.film2nightApp.specification.SearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public void saveFilm(Film film) {
        filmRepository.save(film);
    }

    @Override
    public void saveAllFilms(List<Film> films) {
        filmRepository.saveAll(films);
    }

    @Override
    public void deleteAllFilms() {
        filmRepository.deleteAll();
        log.error("Delete all films");
    }

    @Override
    public Film blockFilmById(long id) {
        Film film = filmRepository.findById(id).get();
        film.setIsBlocked(true);
        filmRepository.save(film);
        return film;
    }

    @Override
    public Film findFilmById(long id) {
        return filmRepository.findById(id).get();
    }

    @Override
    public List<Film> getAvailableFilms() {
        FilmSpecification specification = new FilmSpecification(new SearchCriteria("isBlocked", ":", false));
        return filmRepository.findAll(specification);
    }
}
