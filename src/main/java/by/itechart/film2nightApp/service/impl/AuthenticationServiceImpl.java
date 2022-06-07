package by.itechart.film2nightApp.service.impl;

import by.itechart.film2nightApp.dto.AuthenticationRequestDTO;
import by.itechart.film2nightApp.entity.Role;
import by.itechart.film2nightApp.entity.User;
import by.itechart.film2nightApp.security.jwt.JWTTokenProvider;
import by.itechart.film2nightApp.service.AuthenticationService;
import by.itechart.film2nightApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public Map<Object, Object> response(AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            String username = authenticationRequestDTO.getUsername();
            log.info(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDTO.getPassword()));
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("username with name " + username + " not found");
            }
            List<Role> roleList = new ArrayList<>();
            roleList.addAll(user.getRoles());
            log.info(roleList.toString());
            String token = jwtTokenProvider.createToken(username, roleList);
            log.info(token);
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            return response;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
