package by.itechart.film2nightApp.entity;

import lombok.Data;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@GeneratePojoBuilder
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean isBlocked;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Award> awards = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
}
