package by.itechart.film2nightApp.security.jwt;

import by.itechart.film2nightApp.entity.Role;
import by.itechart.film2nightApp.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JWTUserFactory {

    public JWTUserFactory() {
    }

    public static JWTUser create(User user) {
        return new JWTUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                !user.getIsBlocked(),
                mapToGrantedAuthorities(new HashSet<>(user.getRoles()))

        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
