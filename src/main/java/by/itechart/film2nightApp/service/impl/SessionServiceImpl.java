package by.itechart.film2nightApp.service.impl;

import by.itechart.film2nightApp.dto.*;
import by.itechart.film2nightApp.entity.*;
import by.itechart.film2nightApp.mappers.SessionMapperImpl;
import by.itechart.film2nightApp.repository.SessionRepository;
import by.itechart.film2nightApp.service.FilmService;
import by.itechart.film2nightApp.service.SessionService;
import by.itechart.film2nightApp.service.UserService;
import by.itechart.film2nightApp.specification.SearchCriteria;
import by.itechart.film2nightApp.specification.SessionSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Transactional
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final UserService userService;
    private final FilmService filmService;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, UserService userService, FilmService filmService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
        this.filmService = filmService;
    }

    @Override
    public Session createSession(SessionDto sessionDto) {
        Session session = new SessionBuilder()
                .withCreator(userService.getUserIdFromSecurityContext())
                .withMaxNumberOfGuests(sessionDto.getNumberOfGuests())
                .withTimeBegin(sessionDto.getTimeBegin())
                .withFilm(filmService.findFilmById(sessionDto.getFilmId()))
                .withIsCanceled(false)
                .build();
        sessionRepository.save(session);
        log.info("session saved");
        return session;
    }

    @Override
    public Session sessionCancel(long sessionId) {
        User user = userService.getUserIdFromSecurityContext();
        Session session = sessionRepository.findById(sessionId).get();
        if (user.equals(session.getCreator())) {
            session.setIsCanceled(true);
            sessionRepository.save(session);
        }
        return session;
    }

    @Override
    public Session deleteUserFromSession(long userId, long sessionId) {
        User currentUser = userService.getUserIdFromSecurityContext();
        User user = userService.findById(userId);
        Session session = sessionRepository.findById(sessionId).get();
        if (currentUser.equals(session.getCreator())) {
            session.getUsers().remove(user);
        }
        sessionRepository.save(session);
        return session;
    }

    @Override
    public List<Session> getSessionsByIdCreator() {
        User user = userService.getUserIdFromSecurityContext();
        SessionSpecification specification = new SessionSpecification(new SearchCriteria("creator", ":", user));
        return sessionRepository.findAll(specification);
    }

    @Override
    public List<SessionInfoDto> getAllSessions() {
        List<Session> sessions = sessionRepository.findAllSessions();
        List<SessionInfoDto> sessionInfoDtos = new ArrayList<>();
        for (Session s : sessions) {
            SessionInfoDto sessionInfoDto = new SessionMapperImpl().toSessionInfoDto(s, s.getFilm(), s.getCreator());
            sessionInfoDtos.add(sessionInfoDto);
        }
        return sessionInfoDtos;
    }

    @Override
    public List<SessionInfoDto> getAvailableSession() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        String date = simpleDateFormat.format(timestamp);
        List<Session> sessions = sessionRepository.findAvailableSessions(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(date));
        List<SessionInfoDto> sessionInfoDtoList = new ArrayList<>();
        for (Session s : sessions) {
            SessionInfoDto sessionInfoDto = new SessionMapperImpl().toSessionInfoDto(s, s.getFilm(), s.getCreator());
            sessionInfoDtoList.add(sessionInfoDto);
        }
        return sessionInfoDtoList;
    }

    @Override
    public List<Session> findAllSessionForYear() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Long yearBefore = calendar.getTimeInMillis();
        Timestamp before = new Timestamp(yearBefore);
        String date1 = simpleDateFormat.format(before);
        try {
            return sessionRepository.findAllSessionsForYear(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Session findSessionById(long id) {
        return sessionRepository.findById(id).get();
    }

    @Override
    public void saveSession(Session session) {
        sessionRepository.save(session);
    }

    @Override
    public Session updateSession(SessionUpdateDto sessionUpdateDto) {
        User user = userService.getUserIdFromSecurityContext();
        Session session = sessionRepository.findById(sessionUpdateDto.getSessionId()).get();
        Film film = filmService.findFilmById(sessionUpdateDto.getFilmId());
        if (user.equals(session.getCreator())) {
            session.setFilm(film);
            session.setTimeBegin(sessionUpdateDto.getTimeBegin());
            session.setMaxNumberOfGuests(sessionUpdateDto.getNumberOfGuests());
            sessionRepository.save(session);
        }
        return session;
    }

    @Override
    public SessionReportForYearDto getSessionForYear(int year) throws ParseException {

        int countOfMonth = 12;
        int countOfSessions = 0;
        Map<Month, Integer> countOfSession = new TreeMap<>();
        for (int i = 1; i <= countOfMonth; i++) {
            Month month = Month.of(i);
            LocalDateTime dateBegin = Year.of(year).atMonth(month).atDay(1).atTime(0, 0, 0);
            LocalDateTime dateEnd = Year.of(year).atMonth(month).atEndOfMonth().atTime(0, 0, 0);
            Timestamp timestampBegin = Timestamp.valueOf(dateBegin);
            Timestamp timestampEnd = Timestamp.valueOf(dateEnd);
            List<Session> list = sessionRepository.findByTimeBeginIsBetween(timestampBegin, timestampEnd);
            countOfSession.put(month, list.size());
            countOfSessions += list.size();
            log.info(String.valueOf(list.size()));
        }
        return new SessionReportForYearDto(countOfSession, countOfSessions);
    }

    @Override
    public Map<Month, Integer> getActiveSessionsForMonth(int year, int month) {
        Map<Month, Integer> countOfSessionsForMonth = new HashMap<>();
        Month monthReport = Month.of(month);
        LocalDateTime dateBegin = Year.of(year).atMonth(monthReport).atDay(1).atTime(0, 0, 0);
        LocalDateTime dateEnd = Year.of(year).atMonth(monthReport).atEndOfMonth().atTime(0, 0, 0);
        log.info(dateBegin.toString());
        log.info(dateEnd.toString());
        Timestamp timestampBegin = Timestamp.valueOf(dateBegin);
        Timestamp timestampEnd = Timestamp.valueOf(dateEnd);
        List<Session> list = sessionRepository.findByTimeBeginIsBetweenForMonth(timestampBegin, timestampEnd);
        countOfSessionsForMonth.put(monthReport, list.size());
        return countOfSessionsForMonth;
    }

    @Override
    public SessionReportForYearDto getActiveSessionsForYear(int year) {
        int countOfMonth = 12;
        int countOfSessions = 0;
        Map<Month, Integer> countOfSession = new TreeMap<>();
        for (int i = 1; i <= countOfMonth; i++) {
            Month month = Month.of(i);
            LocalDateTime dateBegin = Year.of(year).atMonth(month).atDay(1).atTime(0, 0, 0);
            LocalDateTime dateEnd = Year.of(year).atMonth(month).atEndOfMonth().atTime(0, 0, 0);
            Timestamp timestampBegin = Timestamp.valueOf(dateBegin);
            Timestamp timestampEnd = Timestamp.valueOf(dateEnd);
            List<Session> list = sessionRepository.findByTimeBeginIsBetweenForMonth(timestampBegin, timestampEnd);
            countOfSession.put(month, list.size());
            countOfSessions += list.size();
            log.info(String.valueOf(list.size()));
        }
        return new SessionReportForYearDto(countOfSession, countOfSessions);
    }

    @Override
    public Map<String, Integer> getActiveSessionsForWeek(int year, int month, int week) {
        Map<String, Integer> countOfSessionsForMonth = new HashMap<>();
        Month monthReport = Month.of(month);
        LocalDateTime dateBegin = Year.of(year).atMonth(monthReport).atDay(1).atTime(0, 0, 0).plusWeeks(week - 1);
        LocalDateTime dateEnd = Year.of(year).atMonth(monthReport).atDay(1).atTime(0, 0, 0).plusWeeks(week);
        log.info(dateBegin.toString());
        log.info(dateEnd.toString());
        Timestamp timestampBegin = Timestamp.valueOf(dateBegin);
        Timestamp timestampEnd = Timestamp.valueOf(dateEnd);
        List<Session> list = sessionRepository.findByTimeBeginIsBetweenForMonth(timestampBegin, timestampEnd);
        countOfSessionsForMonth.put(dateBegin + "  -  " + dateEnd, list.size());
        return countOfSessionsForMonth;
    }

    @Override
    public Map<String, Integer> getActiveSessionsForDay(int year, int month, int day) {
        Map<String, Integer> countOfSessionsForMonth = new HashMap<>();
        Month monthReport = Month.of(month);
        LocalDateTime dateBegin = Year.of(year).atMonth(monthReport).atDay(day).atTime(0, 0, 0);
        LocalDateTime dateEnd = Year.of(year).atMonth(monthReport).atDay(day).atTime(23, 59, 59);
        log.info(dateBegin.toString());
        log.info(dateEnd.toString());
        Timestamp timestampBegin = Timestamp.valueOf(dateBegin);
        Timestamp timestampEnd = Timestamp.valueOf(dateEnd);
        List<Session> list = sessionRepository.findByTimeBeginIsBetweenForMonth(timestampBegin, timestampEnd);
        countOfSessionsForMonth.put(dateBegin + "  -  " + dateEnd, list.size());
        return countOfSessionsForMonth;
    }

    @Override
    public List<ScoreAverageDto> getSessionAverageScore() {
        List<String> objects = sessionRepository.getSessionAverageScore();
        List<String> str;
        List<ScoreAverageDto> scoreAverageDtos = new ArrayList<>();
        for (String o : objects) {
            str = Stream.of(o.split(","))
                    .map(String::new)
                    .collect(Collectors.toList());
            scoreAverageDtos.add(new ScoreAverageDto(str.get(0), str.get(1)));
        }
        return scoreAverageDtos;
    }

    @Override
    public List<ScoreAverageDto> getSessionByScoreRating(int year) {
        LocalDateTime dateBegin = Year.of(year).atMonth(1).atDay(1).atTime(0, 0, 0);
        LocalDateTime dateEnd = Year.of(year).atMonth(12).atEndOfMonth().atTime(0, 0, 0);
        Timestamp timestampBegin = Timestamp.valueOf(dateBegin);
        Timestamp timestampEnd = Timestamp.valueOf(dateEnd);
        List<String> sessions = sessionRepository.getSessionByRatingScore(timestampBegin, timestampEnd);
        List<String> str;
        List<ScoreAverageDto> scoreAverageDtos = new ArrayList<>();
        for (String s : sessions) {
            str = Stream.of(s.split(","))
                    .map(String::new)
                    .collect(Collectors.toList());
            scoreAverageDtos.add(new ScoreAverageDto(str.get(0), str.get(1)));
        }
        return scoreAverageDtos;
    }
}
