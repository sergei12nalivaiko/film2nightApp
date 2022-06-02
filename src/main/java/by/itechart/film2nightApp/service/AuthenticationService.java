package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.dto.AuthenticationRequestDTO;

import java.util.Map;

public interface AuthenticationService {
    Map<Object, Object> response(AuthenticationRequestDTO authenticationRequestDTO);
}
