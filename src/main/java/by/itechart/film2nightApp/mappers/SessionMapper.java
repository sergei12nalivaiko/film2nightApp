package by.itechart.film2nightApp.mappers;

import by.itechart.film2nightApp.dto.SessionInfoDto;
import by.itechart.film2nightApp.entity.Film;
import by.itechart.film2nightApp.entity.Session;
import by.itechart.film2nightApp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface SessionMapper {

    Session toSession(SessionInfoDto sessionInfoDto);

    @Mapping(source = "session.id", target = "id")
    @Mapping(source = "creator.username", target = "username")
    @Mapping(source = "film.kinopoiskId", target = "filmId")
    @Mapping(source = "film.nameOrigin", target = "nameOrigin")
    @Mapping(source = "session.timeBegin", target = "timeBegin")
    @Mapping(source = "session.maxNumberOfGuests", target = "maxNumberOfGuests")
    SessionInfoDto toSessionInfoDto(Session session, Film film, User creator);
}
