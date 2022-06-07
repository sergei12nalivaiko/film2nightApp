package by.itechart.film2nightApp.service.impl;

import by.itechart.film2nightApp.dto.ScoreDto;
import by.itechart.film2nightApp.dto.ScoreInfoDto;
import by.itechart.film2nightApp.entity.Score;
import by.itechart.film2nightApp.entity.ScoreBuilder;
import by.itechart.film2nightApp.entity.Session;
import by.itechart.film2nightApp.entity.User;
import by.itechart.film2nightApp.mappers.ScoreMapperImpl;
import by.itechart.film2nightApp.repository.ScoreRepository;
import by.itechart.film2nightApp.service.ScoreService;
import by.itechart.film2nightApp.service.SessionService;
import by.itechart.film2nightApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository, UserService userService, SessionService sessionService) {
        this.scoreRepository = scoreRepository;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public ScoreInfoDto addScoreToSession(ScoreDto scoreDto) {
        User user = userService.getUserIdFromSecurityContext();
        Session session = sessionService.findSessionById(scoreDto.getSessionId());
        if (session.getUsers().contains(user)) {
            Score score = new ScoreBuilder()
                    .withScore(scoreDto.getScore())
                    .withSession(sessionService.findSessionById(scoreDto.getSessionId()))
                    .withUser(user)
                    .build();
            session.getScores().add(score);
            scoreRepository.save(score);
            return new ScoreMapperImpl().toScoreInfoDto(score, session, user, session.getFilm());
        }
        return null;
    }
}
