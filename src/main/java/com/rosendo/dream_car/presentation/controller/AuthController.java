package com.rosendo.dream_car.presentation.controller;

import com.rosendo.dream_car.application.service.AuthService;
import com.rosendo.dream_car.infrastructure.security.JwtTokenProvider;
import com.rosendo.dream_car.presentation.dto.AccountCredentialsDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dream-car/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AccountCredentialsDto credentials) {
        if (credentialsIsInvalid(credentials)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authService.signIn(credentials);

        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return  ResponseEntity.ok().body(token);
    }

    @PutMapping("/refresh/{username}")
    public ResponseEntity<?> refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
        if (parametersAreInvalid(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authService.refreshToken(username, refreshToken);
        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return  ResponseEntity.ok().body(token);
    }

    @GetMapping("/validade-token")
    public Boolean validateToken(@RequestHeader("Authorization") String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @PostMapping(value = "/createUser",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AccountCredentialsDto create(@RequestBody AccountCredentialsDto credentials) {
        return authService.create(credentials);
    }

    private boolean parametersAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }

    private static boolean credentialsIsInvalid(AccountCredentialsDto credentials) {
        return credentials == null ||
                StringUtils.isBlank(credentials.getPassword()) ||
                (StringUtils.isBlank(credentials.getUsername()) && StringUtils.isBlank(credentials.getEmail()));
    }
}
