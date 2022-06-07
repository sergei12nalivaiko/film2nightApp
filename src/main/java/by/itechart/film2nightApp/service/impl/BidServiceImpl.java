package by.itechart.film2nightApp.service.impl;

import by.itechart.film2nightApp.dto.*;
import by.itechart.film2nightApp.entity.*;
import by.itechart.film2nightApp.mappers.BidMapperImpl;
import by.itechart.film2nightApp.repository.BidRepository;
import by.itechart.film2nightApp.service.*;
import by.itechart.film2nightApp.specification.BidSpecification;
import by.itechart.film2nightApp.specification.SearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class BidServiceImpl implements BidService {


    private final BidRepository bidRepository;
    private final SessionService sessionService;
    private final UserService userService;
    private final FilmService filmService;
    private final BlockingReasonService blockingReasonService;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository, SessionService sessionService, UserService userService,
                          FilmService filmService, BlockingReasonService blockingReasonService) {
        this.bidRepository = bidRepository;
        this.sessionService = sessionService;
        this.userService = userService;
        this.filmService = filmService;
        this.blockingReasonService = blockingReasonService;
    }

    @Override
    public BidInfoDto createBidOnSessionView(BidDto bidDto) {

        Bid bid = new BidBuilder()
                .withBidType(bidDto.getBidType())
                .withStatusType(StatusType.OPEN)
                .withUser(userService.getUserIdFromSecurityContext())
                .withSession(sessionService.findSessionById(bidDto.getSessionId()))
                .build();
        bidRepository.save(bid);
        return new BidMapperImpl().toBidInfoDto(bid.getSession(), bid.getSession().getFilm(), bid);
    }

    @Override
    public BidInfoDto approveBidOnSessionView(long id) {
        User sessionCreator = userService.getUserIdFromSecurityContext();
        Bid bid = bidRepository.findById(id).get();
        if (sessionCreator.getId() == bid.getSession().getCreator().getId()) {
            bid.setStatusType(StatusType.APPROVED);
            bidRepository.save(bid);
            User user = bid.getUser();
            Session session = bid.getSession();
            session.getUsers().add(user);
            sessionService.saveSession(session);
        }
        return new BidMapperImpl().toBidInfoDto(bid.getSession(), bid.getSession().getFilm(), bid);
    }

    @Override
    public BidInfoDto rejectBidOnSessionView(long id) {
        User sessionCreator = userService.getUserIdFromSecurityContext();
        Bid bid = bidRepository.findById(id).get();
        if (sessionCreator.getId() == bid.getSession().getCreator().getId()) {
            bid.setStatusType(StatusType.REJECTED);
            bidRepository.save(bid);
        }
        return new BidMapperImpl().toBidInfoDto(bid.getSession(), bid.getSession().getFilm(), bid);
    }

    @Override
    public List<BidInfoDto> getBidsByIdCreator() {
        User user = userService.getUserIdFromSecurityContext();
        List<BidInfoDto> bidInfoDtos = new ArrayList<>();
        BidSpecification specification = new BidSpecification(new SearchCriteria("user", ":", user));
        List<Bid> bids = bidRepository.findAll(specification);
        for (Bid b : bids) {
            if (b.getSession() == null) {
                bidInfoDtos.add(new BidMapperImpl().toBidInfoDto(null, null, b));
            } else {
                bidInfoDtos.add(new BidMapperImpl().toBidInfoDto(b.getSession(), b.getSession().getFilm(), b));
            }
        }
        return bidInfoDtos;
    }

    @Override
    public List<BidInfoDto> getBidsBySessionCreator() {
        List<Session> session = sessionService.getSessionsByIdCreator();
        log.info(session.toString());
        List<BidInfoDto> bidInfoDtos = new ArrayList<>();
        for (Session s : session) {
            List<List<Bid>> bidList = new ArrayList<>();
            BidSpecification bidSpecification = new BidSpecification(new SearchCriteria("session", ":", s));
            bidList.add(bidRepository.findAll(bidSpecification));
            for (int i = 0; i < bidList.get(0).size(); i++) {
                BidInfoDto bidInfoDto = new BidMapperImpl().toBidInfoDto(bidList.get(0).get(i).getSession(), bidList.get(0).get(i).getSession().getFilm(), bidList.get(0).get(i));
                bidInfoDtos.add(bidInfoDto);
            }
        }
        return bidInfoDtos;
    }

    @Override
    public BidInfoDto deleteBid(long id) {
        User user = userService.getUserIdFromSecurityContext();
        Bid bid = bidRepository.findById(id).get();
        if (user.getId() == bid.getUser().getId()) {
            bid.setStatusType(StatusType.DELETED);
            bid.getSession().getUsers().remove(user);
            bidRepository.save(bid);
        }
        return new BidMapperImpl().toBidInfoDto(bid.getSession(), bid.getSession().getFilm(), bid);
    }

    @Override
    public BidInfoDto createBidOnDeleteFilm(BidDeleteFilmDto bidDeleteFilmDto) {
        User user = userService.getUserIdFromSecurityContext();
        Session session = new Session();
        session.setFilm(filmService.findFilmById(bidDeleteFilmDto.getFilmId()));
        session.setIsCanceled(true);
        sessionService.saveSession(session);
        Bid bid = new BidBuilder()
                .withBidType(BidType.FILM_DELETE)
                .withUser(user)
                .withStatusType(StatusType.OPEN)
                .withSession(session)
                .build();
        bidRepository.save(bid);
        Film film = session.getFilm();
        film.getBlockingReasonSet().add(blockingReasonService.findById(bidDeleteFilmDto.getBlockingReasonId()));
        filmService.saveFilm(film);
        return new BidMapperImpl().toBidInfoDto(bid.getSession(), bid.getSession().getFilm(), bid);
    }

    @Override
    public BidInfoDto createBidOnCancelSession(long id) {
        User user = userService.getUserIdFromSecurityContext();
        Session session = sessionService.findSessionById(id);
        Bid bid = new BidBuilder()
                .withSession(session)
                .withStatusType(StatusType.OPEN)
                .withUser(user)
                .withBidType(BidType.CANCEL_SESSION)
                .build();
        bidRepository.save(bid);
        return new BidMapperImpl().toBidInfoDto(bid.getSession(), bid.getSession().getFilm(), bid);
    }

    @Override
    public BidCancelUserDto createBidOnBlockUser(UserDto userDto) {
        User user = userService.findByUsername(userDto.getUsername());
        Session session = new Session();
        session.setIsCanceled(true);
        sessionService.saveSession(session);
        Bid bid = new BidBuilder()
                .withSession(session)
                .withStatusType(StatusType.OPEN)
                .withUser(user)
                .withBidType(BidType.USER_BLOCK)
                .build();
        bidRepository.save(bid);
        return new BidMapperImpl().toBidCancelUserDto(user, bid);
    }

    @Override
    public BidInfoDto approveBidOnDeleteFilm(long bidId) {
        Bid bid = bidRepository.findById(bidId).get();
        bid.setStatusType(StatusType.APPROVED);
        bidRepository.save(bid);
        Film film = bid.getSession().getFilm();
        film.setIsBlocked(true);
        filmService.saveFilm(film);
        return new BidMapperImpl().toBidInfoDto(bid.getSession(), bid.getSession().getFilm(), bid);
    }

    @Override
    public List<BidInfoDto> getAllBidsForDeleteFilm() {
        List<Bid> bids = bidRepository.findAllBid(BidType.FILM_DELETE);
        List<BidInfoDto> bidInfoDtos = new ArrayList<>();
        for (Bid b : bids) {
            BidInfoDto bidInfoDto = new BidMapperImpl().toBidInfoDto(b.getSession(), b.getSession().getFilm(), b);
            bidInfoDtos.add(bidInfoDto);
        }
        return bidInfoDtos;
    }

    @Override
    public List<BidInfoDto> getAllBidsForCancelSession() {
        List<Bid> bids = bidRepository.findAllBid(BidType.CANCEL_SESSION);
        List<BidInfoDto> bidInfoDtos = new ArrayList<>();
        for (Bid b : bids) {
            BidInfoDto bidInfoDto = new BidMapperImpl().toBidInfoDto(b.getSession(), b.getSession().getFilm(), b);
            bidInfoDtos.add(bidInfoDto);
        }
        return bidInfoDtos;
    }

    @Override
    public BidInfoDto approveBidOnCancelSession(long id) {
        Bid bid = bidRepository.findById(id).get();
        Session session = sessionService.findSessionById(bid.getSession().getId());
        session.setIsCanceled(true);
        sessionService.saveSession(session);
        bid.setStatusType(StatusType.APPROVED);
        bidRepository.save(bid);
        BidInfoDto bidInfoDto = new BidMapperImpl().toBidInfoDto(bid.getSession(), bid.getSession().getFilm(), bid);
        return bidInfoDto;
    }

    @Override
    public BidInfoDto rejectBidByModerator(long id) {
        Bid bid = bidRepository.findById(id).get();
        bid.setStatusType(StatusType.REJECTED);
        bidRepository.save(bid);
        return new BidMapperImpl().toBidInfoDto(bid.getSession(), bid.getSession().getFilm(), bid);
    }

    @Override
    public List<BidCancelUserDto> getAllBidsForBlockUser() {
        List<Bid> bids = bidRepository.findAllBid(BidType.USER_BLOCK);
        List<BidCancelUserDto> bidCancelUserDtos = new ArrayList<>();
        for (Bid b : bids) {
            BidCancelUserDto bidCancelUserDto = new BidMapperImpl().toBidCancelUserDto(b.getUser(), b);
            bidCancelUserDtos.add(bidCancelUserDto);
        }
        return bidCancelUserDtos;
    }

    @Override
    public BidCancelUserDto approveBidOnBlockUser(long id) {
        Bid bid = bidRepository.findById(id).get();
        User user = bid.getUser();
        user.setIsBlocked(true);
        userService.saveUser(user);
        bid.setStatusType(StatusType.APPROVED);
        bidRepository.save(bid);
        return new BidMapperImpl().toBidCancelUserDto(user, bid);
    }
}
