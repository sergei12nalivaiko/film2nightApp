package by.itechart.film2nightApp.entity;

import lombok.Data;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;

@Data
@Entity
@GeneratePojoBuilder
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Session session;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType statusType;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BidType bidType;
}
