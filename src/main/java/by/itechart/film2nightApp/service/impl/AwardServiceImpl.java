package by.itechart.film2nightApp.service.impl;

import by.itechart.film2nightApp.dto.AwardDto;
import by.itechart.film2nightApp.dto.AwardInfoDto;
import by.itechart.film2nightApp.dto.AwardSessionDto;
import by.itechart.film2nightApp.entity.Award;
import by.itechart.film2nightApp.entity.Session;
import by.itechart.film2nightApp.entity.User;
import by.itechart.film2nightApp.mappers.AwardMapperImpl;
import by.itechart.film2nightApp.repository.AwardRepository;
import by.itechart.film2nightApp.service.AwardService;
import by.itechart.film2nightApp.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class AwardServiceImpl implements AwardService {

    private final AwardRepository awardRepository;
    private final SessionService sessionService;

    @Autowired
    public AwardServiceImpl(AwardRepository awardRepository, SessionService sessionService) {
        this.awardRepository = awardRepository;
        this.sessionService = sessionService;
    }

    @Override
    public Map<String, String> saveAward(AwardDto awardDto) {
        Award award = new AwardMapperImpl().toAward(awardDto);
        awardRepository.save(award);
        Map<String, String> response = new HashMap<>();
        response.put("name", award.getName());
        return response;
    }

    @Override
    public AwardSessionDto setAwardToSession(AwardInfoDto awardInfoDto) {
        Session session = sessionService.findSessionById(awardInfoDto.getSessionId());
        User user = session.getCreator();
        Award award = awardRepository.getById(awardInfoDto.getAwardId());
        user.getAwards().add(award);
        return new AwardMapperImpl().toAwardSessionDto(session, award);
    }
}
