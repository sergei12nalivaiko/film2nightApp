package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.dto.ScoreDto;
import by.itechart.film2nightApp.dto.ScoreInfoDto;

public interface ScoreService {
    ScoreInfoDto addScoreToSession(ScoreDto scoreDto);
}
