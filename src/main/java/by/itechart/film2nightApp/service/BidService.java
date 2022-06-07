package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.dto.*;

import java.util.List;

public interface BidService {

    BidInfoDto createBidOnSessionView(BidDto bidDto);

    BidInfoDto createBidOnDeleteFilm(BidDeleteFilmDto bidDeleteFilmDto);

    BidInfoDto createBidOnCancelSession(long id);

    BidCancelUserDto createBidOnBlockUser(UserDto userDto);

    BidInfoDto approveBidOnSessionView(long id);

    BidInfoDto approveBidOnDeleteFilm(long bidId);

    BidInfoDto rejectBidOnSessionView(long id);

    List<BidInfoDto> getBidsByIdCreator();

    List<BidInfoDto> getBidsBySessionCreator();

    BidInfoDto deleteBid(long id);

    List<BidInfoDto> getAllBidsForDeleteFilm();

    List<BidInfoDto> getAllBidsForCancelSession();

    List<BidCancelUserDto> getAllBidsForBlockUser();

    BidInfoDto approveBidOnCancelSession(long id);

    BidInfoDto rejectBidByModerator(long id);

    BidCancelUserDto approveBidOnBlockUser(long id);
}
