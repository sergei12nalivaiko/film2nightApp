package by.itechart.film2nightApp.dto;

import by.itechart.film2nightApp.entity.BidType;
import lombok.Data;

@Data
public class BidDto {
    private long sessionId;
    private BidType bidType;
}
