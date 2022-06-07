package by.itechart.film2nightApp.entity;

import lombok.*;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@GeneratePojoBuilder
public class Film {
    @Id
    @Column(nullable = false)
    private Long kinopoiskId;
    @Column(nullable = false)
    private String nameOrigin;
    @Column(nullable = false)
    private String posterUrl;
    @Column(nullable = false)
    private Float ratingKinopoisk;
    @Column(nullable = false)
    private int ratingKinopoiskVoteCount;
    @Column(nullable = false)
    private String webUrl;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private int filmLength;
    @Column(nullable = false)
    private String lastSync;
    @Column(nullable = false)
    private Boolean isBlocked;
    @ManyToMany
    private Set<BlockingReason> blockingReasonSet = new HashSet<>();
}
