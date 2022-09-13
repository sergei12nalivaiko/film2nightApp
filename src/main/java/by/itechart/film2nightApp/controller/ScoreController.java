package by.itechart.film2nightApp.controller;

import by.itechart.film2nightApp.dto.ScoreDto;
import by.itechart.film2nightApp.dto.ScoreInfoDto;
import by.itechart.film2nightApp.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/")
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/score/add")
    public ResponseEntity<ScoreInfoDto> addScoreToSession(@RequestBody ScoreDto scoreDto) {
        return ResponseEntity.ok().body(scoreService.addScoreToSession(scoreDto));
    }

}
