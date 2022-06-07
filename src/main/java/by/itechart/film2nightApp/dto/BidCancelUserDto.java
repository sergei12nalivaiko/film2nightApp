package by.itechart.film2nightApp.dto;

import by.itechart.film2nightApp.entity.BidType;
import by.itechart.film2nightApp.entity.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BidCancelUserDto {
    private long bidId;
    private String username;
    private Boolean isBlocked;
    private String statusType;
    private String bidType;
}
