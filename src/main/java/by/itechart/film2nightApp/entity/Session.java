package by.itechart.film2nightApp.entity;


import lombok.Data;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@GeneratePojoBuilder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User creator;
    @ManyToOne
    private Film film;
    private Boolean isCanceled;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeBegin;
    private Long maxNumberOfGuests;
    @ManyToMany
    private List<Score> scores = new ArrayList<>();
    @ManyToMany
    private Set<User> users = new HashSet<>();
}
