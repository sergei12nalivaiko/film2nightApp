package by.itechart.film2nightApp.repository;

import by.itechart.film2nightApp.entity.Bid;
import by.itechart.film2nightApp.entity.BidType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long>, JpaSpecificationExecutor<Bid> {

    @Query("select b from Bid b where b.bidType = :bidType")
    List<Bid> findAllBid(@Param("bidType") BidType bidType);
}
