package by.itechart.film2nightApp.dto;

import by.itechart.film2nightApp.entity.BidType;
import by.itechart.film2nightApp.entity.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BidInfoDto {

    private Long bidId;
    private long sessionId;
    private String username;
    private Long filmId;
    private String nameOrigin;
    private boolean isBlocked;
    private StatusType statusType;
    private BidType bidType;
}
