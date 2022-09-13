package by.itechart.film2nightApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreDto {
    private int score;
    private long sessionId;
    private long userId;
}
