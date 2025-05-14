package com.rosendo.dream_car.presentation.controller;

import com.rosendo.dream_car.application.service.AuthService;
import com.rosendo.dream_car.infrastructure.security.JwtTokenProvider;
import com.rosendo.dream_car.presentation.dto.AccountCredentialsDto;
import com.rosendo.dream_car.presentation.dto.TokenDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> signIn(
            @RequestBody AccountCredentialsDto credentials,
            HttpServletResponse response) {
        if (credentialsIsInvalid(credentials))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        
        TokenDto token = authService.signIn(credentials);
        if (token == null) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        
        jwtTokenProvider.setTokenCookies(response, token);
        return ResponseEntity.ok().body(token);
    }

    @PutMapping("/refresh/{username}")
    public ResponseEntity<?> refreshToken(
            @PathVariable("username") String username,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        
        if (parametersAreInvalid(username, refreshToken)) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        
        TokenDto token = authService.refreshToken(username, refreshToken);
        if (token == null) 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        
        jwtTokenProvider.setTokenCookies(response, token);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOut(HttpServletResponse response) {
        jwtTokenProvider.clearTokenCookies(response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate-token")
    public Boolean validateToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        return token != null && jwtTokenProvider.validateToken(token);
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
