package by.itechart.film2nightApp.repository;

import by.itechart.film2nightApp.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film,Integer> {
}
