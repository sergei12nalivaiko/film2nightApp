package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.dto.*;
import by.itechart.film2nightApp.entity.Session;

import java.text.ParseException;
import java.time.Month;
import java.util.List;
import java.util.Map;

public interface SessionService {
    Session createSession(SessionDto sessionDto);

    Session sessionCancel(long sessionId);

    Session deleteUserFromSession(long userId, long sessionId);

    List<Session> getSessionsByIdCreator();

    List<SessionInfoDto> getAllSessions();

    List<SessionInfoDto> getAvailableSession() throws ParseException;

    List<Session> findAllSessionForYear();

    Session findSessionById(long id);

    void saveSession(Session session);

    Session updateSession(SessionUpdateDto sessionUpdateDto);

    SessionReportForYearDto getSessionForYear(int year) throws ParseException;

    SessionReportForYearDto getActiveSessionsForYear(int year);

    Map<Month, Integer> getActiveSessionsForMonth(int year, int month);

    Map<String, Integer> getActiveSessionsForWeek(int year, int month, int week);

    Map<String, Integer> getActiveSessionsForDay(int year, int month, int day);

    List<ScoreAverageDto> getSessionAverageScore();

    List<ScoreAverageDto> getSessionByScoreRating(int year);
}
