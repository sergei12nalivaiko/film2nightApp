package by.itechart.film2nightApp.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SessionUpdateDto {
    private long sessionId;
    private long filmId;
    private long numberOfGuests;
    private Timestamp timeBegin;
}
