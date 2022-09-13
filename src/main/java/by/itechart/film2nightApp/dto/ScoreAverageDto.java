package by.itechart.film2nightApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreAverageDto {
    private String sessionId;
    private String score;
}
