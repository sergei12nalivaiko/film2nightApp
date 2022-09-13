package by.itechart.film2nightApp.service.impl;

import by.itechart.film2nightApp.dto.CommentDto;
import by.itechart.film2nightApp.dto.CommentInfoDto;
import by.itechart.film2nightApp.entity.Comment;
import by.itechart.film2nightApp.entity.CommentBuilder;
import by.itechart.film2nightApp.entity.Session;
import by.itechart.film2nightApp.entity.User;
import by.itechart.film2nightApp.mappers.CommentMapperImpl;
import by.itechart.film2nightApp.repository.CommentRepository;
import by.itechart.film2nightApp.service.CommentService;
import by.itechart.film2nightApp.service.SessionService;
import by.itechart.film2nightApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, SessionService sessionService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public CommentInfoDto addCommentToSession(CommentDto commentDto) {
        User user = userService.getUserIdFromSecurityContext();
        Session session = sessionService.findSessionById(commentDto.getSessionId());
        if (session.getUsers().contains(user)) {
            Comment comment = new CommentBuilder()
                    .withComment(commentDto.getMessage())
                    .withSession(session)
                    .withUser(user)
                    .build();
            commentRepository.save(comment);
        }
        return new CommentMapperImpl().toCommentInfoDto(session, session.getFilm(), user, commentDto.getMessage());
    }
}
