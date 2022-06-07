package by.itechart.film2nightApp.controller;

import by.itechart.film2nightApp.dto.AwardDto;
import by.itechart.film2nightApp.dto.AwardInfoDto;
import by.itechart.film2nightApp.service.AwardService;
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
public class AwardController {

    public final AwardService awardService;

    @Autowired
    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/award/save")
    public ResponseEntity saveAward(@RequestBody AwardDto awardDto) {
        return ResponseEntity.ok(awardService.saveAward(awardDto));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @PostMapping("/award/user/set")
    public ResponseEntity setAward(@RequestBody AwardInfoDto awardInfoDto) {
        return ResponseEntity.ok(awardService.setAwardToSession(awardInfoDto));
    }
}
