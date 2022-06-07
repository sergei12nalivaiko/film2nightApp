package by.itechart.film2nightApp.mappers;

import by.itechart.film2nightApp.dto.ScoreInfoDto;
import by.itechart.film2nightApp.entity.Film;
import by.itechart.film2nightApp.entity.Score;
import by.itechart.film2nightApp.entity.Session;
import by.itechart.film2nightApp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ScoreMapper {
    Score toScore(ScoreInfoDto scoreInfoDto);

    @Mapping(source = "session.id", target = "id")
    @Mapping(source = "score.score", target = "score")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "film.kinopoiskId", target = "filmId")
    @Mapping(source = "film.nameOrigin", target = "nameOrigin")
    ScoreInfoDto toScoreInfoDto(Score score, Session session, User user, Film film);
}
