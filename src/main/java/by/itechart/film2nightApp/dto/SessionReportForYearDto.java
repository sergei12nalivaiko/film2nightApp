package by.itechart.film2nightApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Month;
import java.util.Map;

@Data
@AllArgsConstructor
public class SessionReportForYearDto {
    private Map<Month, Integer> mapSessions;
    private long countOfSessions;
}
