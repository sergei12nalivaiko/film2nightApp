package by.itechart.film2nightApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreInfoDto {
    private Long id;
    private String username;
    private Long filmId;
    private String nameOrigin;
    private int score;
}
