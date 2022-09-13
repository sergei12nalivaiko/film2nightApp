package by.itechart.film2nightApp.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SessionInfoDto {
    private Long id;
    private String username;
    private Long filmId;
    private String nameOrigin;
    private Date timeBegin;
    private Long maxNumberOfGuests;
}
