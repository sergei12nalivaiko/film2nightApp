package by.itechart.film2nightApp.controller;

import by.itechart.film2nightApp.dto.*;
import by.itechart.film2nightApp.service.BidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class BidController {

    private final BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/bid/create")
    public ResponseEntity<BidInfoDto> createBidOnSessionView(@RequestBody BidDto bidDto) {
        return ResponseEntity.ok().body(bidService.createBidOnSessionView(bidDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @DeleteMapping("/bid/film/delete")
    public ResponseEntity<BidInfoDto> createBidOnSessionView(@RequestBody BidDeleteFilmDto bidDeleteFilmDto) {
        return ResponseEntity.ok().body(bidService.createBidOnDeleteFilm(bidDeleteFilmDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/bid/session/cancel/{id}")
    public ResponseEntity<BidInfoDto> createBidOnSessionCancel(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok().body(bidService.createBidOnCancelSession(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/bid/block/user")
    public ResponseEntity<BidCancelUserDto> createBidOnBlockUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(bidService.createBidOnBlockUser(userDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/bid/approve/{id}")
    public ResponseEntity<BidInfoDto> approveBid(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok().body(bidService.approveBidOnSessionView(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/bid/reject/{id}")
    public ResponseEntity<BidInfoDto> rejectBidOnSessionView(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok().body(bidService.rejectBidOnSessionView(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/bid/creator")
    public ResponseEntity<List<BidInfoDto>> getAllBidsByIdCreator() {
        return ResponseEntity.ok().body(bidService.getBidsByIdCreator());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @GetMapping("/bid/session/creator")
    public ResponseEntity<List<BidInfoDto>> getAllBidsBySessionIdCreator() {
        return ResponseEntity.ok().body(bidService.getBidsBySessionCreator());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @DeleteMapping("/bid/delete/{id}")
    public ResponseEntity<BidInfoDto> deleteBid(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok().body(bidService.deleteBid(id));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @PostMapping("/bid/film/delete/approve/{id}")
    public ResponseEntity<BidInfoDto> approveBidOnDeleteFilm(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok().body(bidService.approveBidOnDeleteFilm(id));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/bids/delete/films")
    public ResponseEntity<List<BidInfoDto>> getAllBidsForFilmDelete() {
        return ResponseEntity.ok().body(bidService.getAllBidsForDeleteFilm());
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/bids/cancel/session")
    public ResponseEntity<List<BidInfoDto>> getAllBidsForCancelSession() {
        return ResponseEntity.ok().body(bidService.getAllBidsForCancelSession());
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/bids/user/block")
    public ResponseEntity<List<BidCancelUserDto>> getAllBidsForBlockUser() {
        return ResponseEntity.ok().body(bidService.getAllBidsForBlockUser());
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @PostMapping("/bid/session/cancel/approve/{id}")
    public ResponseEntity<BidInfoDto> approveBidOnCancelSession(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok().body(bidService.approveBidOnCancelSession(id));
    }

    @PreAuthorize("hasAnyAuthority('MODERATOR')")
    @PostMapping("/bid/reject/moderator/{id}")
    public ResponseEntity<BidInfoDto> rejectBidByModerator(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok().body(bidService.rejectBidByModerator(id));
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @PostMapping("/bid/block/user/approve/{id}")
    public ResponseEntity<BidCancelUserDto> approveBidOnBlockUser(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok().body(bidService.approveBidOnBlockUser(id));
    }
}
