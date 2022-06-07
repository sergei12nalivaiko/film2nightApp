package by.itechart.film2nightApp.controller;

import by.itechart.film2nightApp.dto.*;
import by.itechart.film2nightApp.entity.Session;
import by.itechart.film2nightApp.service.FilmService;
import by.itechart.film2nightApp.service.SessionService;
import by.itechart.film2nightApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class SessionController {

    private final UserService userService;
    private final FilmService filmService;
    private final SessionService sessionService;

    @Autowired
    public SessionController(UserService userService, FilmService filmService, SessionService sessionService) {
        this.userService = userService;
        this.filmService = filmService;
        this.sessionService = sessionService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/session/create")
    public ResponseEntity<Session> createSession(@RequestBody SessionDto sessionDto) {
        return ResponseEntity.ok().body(sessionService.createSession(sessionDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/session/update")
    public ResponseEntity<Session> updateSession(@RequestBody SessionUpdateDto sessionUpdateDto) {
        return ResponseEntity.ok().body(sessionService.updateSession(sessionUpdateDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/sessions")
    public ResponseEntity<List<SessionInfoDto>> getAllSession() {
        return ResponseEntity.ok().body(sessionService.getAllSessions());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/sessions/creator")
    public ResponseEntity<List<Session>> getSessionsByIdCreator() {
        return ResponseEntity.ok().body(sessionService.getSessionsByIdCreator());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/available/sessions")
    public ResponseEntity<List<SessionInfoDto>> getAvailableSession() throws ParseException {
        return ResponseEntity.ok().body(sessionService.getAvailableSession());
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @PostMapping("/cancel/session/{id}")
    public ResponseEntity<Session> sessionCancel(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok().body(sessionService.sessionCancel(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/sessions/year")
    public ResponseEntity<List<Session>> getAllSessionForYear() {
        return ResponseEntity.ok().body(sessionService.findAllSessionForYear());
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @DeleteMapping("/session/user/delete")
    public ResponseEntity<Session> deleteUserFromSession(@RequestBody UserSessionDto userSessionDto) {
        return ResponseEntity.ok().body(sessionService.deleteUserFromSession(userSessionDto.getUserId(), userSessionDto.getSessionId()));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/sessions/count/{year}")
    public ResponseEntity getSessionsForYear(@PathVariable(name = "year") int year) throws ParseException {
        return ResponseEntity.ok().body(sessionService.getSessionForYear(year));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/sessions/count/active/{year}")
    public ResponseEntity getActiveSessionsForYear(@PathVariable(name = "year") int year) throws ParseException {
        return ResponseEntity.ok().body(sessionService.getActiveSessionsForYear(year));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/sessions/count/{year}/{month}")
    public ResponseEntity getSessionsForMonth(@PathVariable(name = "year") int year, @PathVariable(name = "month") int month) {
        return ResponseEntity.ok().body(sessionService.getActiveSessionsForMonth(year, month));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/sessions/count/active/{year}/{month}/{week}")
    public ResponseEntity getSessionsForWeek(@PathVariable(name = "year") int year, @PathVariable(name = "month") int month, @PathVariable(name = "week") int week) {
        return ResponseEntity.ok().body(sessionService.getActiveSessionsForWeek(year, month, week));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/sessions/count/active/day/{year}/{month}/{day}")
    public ResponseEntity getSessionsForDay(@PathVariable(name = "year") int year, @PathVariable(name = "month") int month, @PathVariable(name = "day") int day) {
        return ResponseEntity.ok().body(sessionService.getActiveSessionsForDay(year, month, day));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/sessions/average/score")
    public ResponseEntity<List<ScoreAverageDto>> getSessionsAverageScore() {
        return ResponseEntity.ok().body(sessionService.getSessionAverageScore());
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/sessions/rating/{year}")
    public ResponseEntity<List<ScoreAverageDto>> getSessionsRating(@PathVariable(name = "year") int year) {
        return ResponseEntity.ok().body(sessionService.getSessionByScoreRating(year));
    }
}
