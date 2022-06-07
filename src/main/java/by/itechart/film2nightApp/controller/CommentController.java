package by.itechart.film2nightApp.controller;

import by.itechart.film2nightApp.dto.CommentDto;
import by.itechart.film2nightApp.dto.CommentInfoDto;
import by.itechart.film2nightApp.service.CommentService;
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
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','USER')")
    @PostMapping("/comment/create")
    public ResponseEntity<CommentInfoDto> createComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok().body(commentService.addCommentToSession(commentDto));
    }
}
