package by.itechart.film2nightApp.controller;

import by.itechart.film2nightApp.dto.AuthenticationRequestDTO;
import by.itechart.film2nightApp.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity.ok(authenticationService.response(authenticationRequestDTO));
    }
}
