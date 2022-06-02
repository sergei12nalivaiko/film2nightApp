package by.itechart.film2nightApp.security;

import by.itechart.film2nightApp.entity.User;
import by.itechart.film2nightApp.security.jwt.JWTUser;
import by.itechart.film2nightApp.security.jwt.JWTUserFactory;
import by.itechart.film2nightApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JWTUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JWTUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);

        if (username == null) {
            throw new UsernameNotFoundException("user with username " + username + " not found");
        }

        JWTUser jwtUser = JWTUserFactory.create(user);
        log.info("in loaduserByUserName - user with username {} successfuly loaded", username);
        log.info(jwtUser.toString());

        return jwtUser;
    }
}
