package by.itechart.film2nightApp.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private User creator;
    @OneToOne
    private Film film;
    @Column(nullable = false)
    private int maxQuantityOfUsers;
    @Column(nullable = false)
    private  String is_canceled;
    @Column(nullable = false)
    private Timestamp timeBegin;
    @OneToOne
    private BlockingReason blockingReason;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    public Session() {
    }

    public int getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public int getMaxQuantityOfUsers() {
        return maxQuantityOfUsers;
    }

    public void setMaxQuantityOfUsers(int maxQuantityOfUsers) {
        this.maxQuantityOfUsers = maxQuantityOfUsers;
    }

    public String getIs_canceled() {
        return is_canceled;
    }

    public void setIs_canceled(String is_canceled) {
        this.is_canceled = is_canceled;
    }

    public Timestamp getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Timestamp timeBegin) {
        this.timeBegin = timeBegin;
    }

    public BlockingReason getBlockingReason() {
        return blockingReason;
    }

    public void setBlockingReason(BlockingReason blockingReason) {
        this.blockingReason = blockingReason;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return id == session.id && maxQuantityOfUsers == session.maxQuantityOfUsers &&
                Objects.equals(creator, session.creator) && Objects.equals(film, session.film) &&
                Objects.equals(is_canceled, session.is_canceled) && Objects.equals(timeBegin, session.timeBegin) &&
                Objects.equals(blockingReason, session.blockingReason) && Objects.equals(users, session.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creator, film, maxQuantityOfUsers, is_canceled, timeBegin, blockingReason, users);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", creator=" + creator +
                ", film=" + film +
                ", maxQuantityOfUsers=" + maxQuantityOfUsers +
                ", is_canceled='" + is_canceled + '\'' +
                ", timeBegin=" + timeBegin +
                ", blockingReason=" + blockingReason +
                ", users=" + users +
                '}';
    }
}
