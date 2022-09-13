package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.dto.CommentDto;
import by.itechart.film2nightApp.dto.CommentInfoDto;

public interface CommentService {
    CommentInfoDto addCommentToSession(CommentDto commentDto);
}
