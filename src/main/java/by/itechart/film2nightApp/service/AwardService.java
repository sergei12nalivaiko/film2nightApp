package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.dto.AwardDto;
import by.itechart.film2nightApp.dto.AwardInfoDto;
import by.itechart.film2nightApp.dto.AwardSessionDto;

import java.util.Map;

public interface AwardService {
    Map<String, String> saveAward(AwardDto awardDto);

    AwardSessionDto setAwardToSession(AwardInfoDto awardInfoDto);
}
