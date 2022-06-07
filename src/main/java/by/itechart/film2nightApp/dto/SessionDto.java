package by.itechart.film2nightApp.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SessionDto {
    private long filmId;
    private long numberOfGuests;
    private Timestamp timeBegin;
}
