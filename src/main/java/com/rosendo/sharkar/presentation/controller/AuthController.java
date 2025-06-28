package com.rosendo.sharkar.presentation.controller;

import com.rosendo.sharkar.application.service.AuthService;
import com.rosendo.sharkar.domain.model.User;
import com.rosendo.sharkar.infrastructure.security.JwtTokenProvider;
import com.rosendo.sharkar.presentation.dto.AccountCredentialsDto;
import com.rosendo.sharkar.presentation.dto.TokenDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sharkar/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AccountCredentialsDto credentials, HttpServletResponse response) {
        if (authService.credentialsIsInvalid(credentials))
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
        
        if (authService.parametersAreInvalid(username, refreshToken))
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

    @GetMapping(value = "/user/{username}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountCredentialsDto getUserInfo(@PathVariable("username") String username) {

        var userInfo = authService.getByEmailOrUsername(username, username);

        return new AccountCredentialsDto(
                userInfo.getUsername(),
                userInfo.getEmail(),
                userInfo.getPassword(),
                userInfo.getFullName()
        );
    }

    @PutMapping("/user/updateInfo")
    public ResponseEntity<?> updateUser(@RequestBody AccountCredentialsDto credentials) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.updateUser(credentials));
    }

    @PutMapping("/user/updatePassword")
    public ResponseEntity<?> updateUserPassword(@RequestBody AccountCredentialsDto credentials) {
        User user = authService.getByEmailOrUsername(credentials.getEmail(), credentials.getUsername());

        if ( user == null || !user.getFullName().equals(credentials.getFullName()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        return ResponseEntity.status(HttpStatus.OK).body(authService.updateUserPassword(credentials));
    }
}
