package by.itechart.film2nightApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AwardSessionDto {
    private long sessionId;
    private String username;
    private String awardName;
    private long id;
}
