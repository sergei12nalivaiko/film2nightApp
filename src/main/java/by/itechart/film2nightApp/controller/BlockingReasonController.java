package by.itechart.film2nightApp.controller;

import by.itechart.film2nightApp.dto.BlockingReasonDto;
import by.itechart.film2nightApp.entity.BlockingReason;
import by.itechart.film2nightApp.service.BlockingReasonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class BlockingReasonController {

    public final BlockingReasonService blockingReasonService;

    public BlockingReasonController(BlockingReasonService blockingReasonService) {
        this.blockingReasonService = blockingReasonService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/blockingreason/save")
    public ResponseEntity saveBlockingReason(@RequestBody BlockingReasonDto blockingReasonDto) {
        return ResponseEntity.ok(blockingReasonService.saveBlockingReason(blockingReasonDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/blockingreasons/get")
    public ResponseEntity<List<BlockingReason>> getAllBlockingReason() {
        return ResponseEntity.ok(blockingReasonService.getBlockingReasons());
    }
}
