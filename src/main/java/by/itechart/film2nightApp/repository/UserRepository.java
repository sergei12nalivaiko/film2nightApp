package by.itechart.film2nightApp.repository;

import by.itechart.film2nightApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("select distinct s.creator from Session s where s.timeBegin between :begin and :end and s.creator.isBlocked = false")
    List<User> findActiveUsers(@Param("begin") Timestamp begin, @Param("end") Timestamp end);
}
