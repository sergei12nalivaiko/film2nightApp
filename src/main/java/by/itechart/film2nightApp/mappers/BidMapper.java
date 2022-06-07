package by.itechart.film2nightApp.mappers;

import by.itechart.film2nightApp.dto.BidCancelUserDto;
import by.itechart.film2nightApp.dto.BidInfoDto;
import by.itechart.film2nightApp.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BidMapper {

    Bid toBid(BidInfoDto bidInfoDto);

    @Mapping(source = "bid.id", target = "bidId")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "bid.user.username", target = "username")
    @Mapping(source = "film.kinopoiskId", target = "filmId")
    @Mapping(source = "film.nameOrigin", target = "nameOrigin")
    @Mapping(source = "film.isBlocked", target = "isBlocked")
    @Mapping(source = "bid.statusType", target = "statusType")
    @Mapping(source = "bid.bidType", target = "bidType")
    BidInfoDto toBidInfoDto(Session session, Film film, Bid bid);

    @Mapping(source = "bid.id", target = "bidId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.isBlocked", target = "isBlocked")
    @Mapping(source = "bid.statusType", target = "statusType")
    @Mapping(source = "bid.bidType", target = "bidType")
    BidCancelUserDto toBidCancelUserDto(User user, Bid bid);
}
