package com.rosendo.dream_car.presentation.controller;

import com.rosendo.dream_car.application.service.AuthService;
import com.rosendo.dream_car.domain.repository.UserRepository;
import com.rosendo.dream_car.domain.service.UserService;
import com.rosendo.dream_car.infrastructure.security.JwtTokenProvider;
import com.rosendo.dream_car.presentation.dto.AccountCredentialsDto;
import com.rosendo.dream_car.presentation.dto.TokenDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @PutMapping("/user/updateInfo/")
    public ResponseEntity<?> updateUser(@RequestBody AccountCredentialsDto credentials) {
        if (authService.getByEmailOrUsername(credentials.getUsername(), credentials.getPassword()) == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return ResponseEntity.status(HttpStatus.OK).body(authService.updateUser(credentials));
    }
}
