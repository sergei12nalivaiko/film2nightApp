package by.itechart.film2nightApp.repository;

import by.itechart.film2nightApp.entity.Session;
import by.itechart.film2nightApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long>, JpaSpecificationExecutor<Session> {

    @Query("select s from Session s where s.timeBegin > current_date and s.users.size < s.maxNumberOfGuests and s.isCanceled = false ")
    List<Session> findAvailableSessions(@Param("current_date") Date current_date);

    @Query("select s from Session s where s.timeBegin > :yearBefore")
    List<Session> findAllSessionsForYear(@Param("yearBefore") Date yearBefore);

    List<Session> findByTimeBeginIsBetween(Timestamp begin, Timestamp end);

    @Query("select s from Session s where s.timeBegin between :begin and :end and s.users.size < s.maxNumberOfGuests and s.isCanceled = false ")
    List<Session> findByTimeBeginIsBetweenForMonth(@Param("begin") Timestamp begin, @Param("end") Timestamp end);

    @Query("select s.creator from Session s where s.timeBegin between :begin and :end and s.creator.isBlocked = false")
    List<User> findActiveUsers(@Param("begin") Timestamp begin, @Param("end") Timestamp end);


    @Query(value = "select session.id, avg(score.score)\n" +
            "from session\n" +
            "join session_scores on session_scores.session_id = session.id\n" +
            "join score on session_scores.scores_id = score.id\n" +
            "group by session.id", nativeQuery = true)
    List<Object> getSessionAverageScore();

}
