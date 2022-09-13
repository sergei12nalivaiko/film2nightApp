package by.itechart.film2nightApp.mappers;

import by.itechart.film2nightApp.dto.CommentInfoDto;
import by.itechart.film2nightApp.entity.Comment;
import by.itechart.film2nightApp.entity.Film;
import by.itechart.film2nightApp.entity.Session;
import by.itechart.film2nightApp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {

    Comment toComment(CommentInfoDto CommentInfoDto);

    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "creator.username", target = "username")
    @Mapping(source = "film.kinopoiskId", target = "filmId")
    @Mapping(source = "film.nameOrigin", target = "nameOrigin")
    @Mapping(source = "session.timeBegin", target = "timeBegin")
    @Mapping(source = "message", target = "message")
    CommentInfoDto toCommentInfoDto(Session session, Film film, User creator, String message);
}
