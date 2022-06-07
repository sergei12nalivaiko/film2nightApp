package by.itechart.film2nightApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentInfoDto {

    private long sessionId;
    private String username;
    private Long filmId;
    private String nameOrigin;
    private Date timeBegin;
    private String message;
}
