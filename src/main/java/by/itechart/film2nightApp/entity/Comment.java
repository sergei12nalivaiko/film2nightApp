package by.itechart.film2nightApp.entity;


import lombok.Data;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;


@Data
@Entity
@GeneratePojoBuilder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String comment;
    @ManyToOne
    private Session session;
    @ManyToOne
    private User user;
}
