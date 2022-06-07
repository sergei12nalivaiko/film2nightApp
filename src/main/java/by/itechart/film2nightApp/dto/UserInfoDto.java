package by.itechart.film2nightApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private long userId;
    private String username;
    private Boolean isBlocked;
}
