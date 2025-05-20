package com.rosendo.dream_car.application.service;

import com.rosendo.dream_car.presentation.dto.AccountCredentialsDto;
import com.rosendo.dream_car.presentation.dto.TokenDto;
import com.rosendo.dream_car.domain.model.User;
import com.rosendo.dream_car.domain.repository.UserRepository;
import com.rosendo.dream_car.exception.RequiredObjectIsNullException;
import com.rosendo.dream_car.infrastructure.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    public TokenDto signIn(AccountCredentialsDto credentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getEmail() != null ? credentials.getEmail() : credentials.getUsername(),
                        credentials.getPassword()
                )
        );
        var user = getEmailOrUsername(credentials.getEmail(), credentials.getUsername());

        return tokenProvider.createAccessToken(
                user.getUsername(),
                user.getEmail(),
                user.getRoles()
        );
    }

    public TokenDto refreshToken(String username, String refreshToken) {
        var user = repository.findByUsername(username);
        TokenDto token;
        if (user != null) {
            token = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        return token;
    }

    public AccountCredentialsDto create(AccountCredentialsDto user) {

        if (user == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one new User!");
        var entity = new User();
        entity.setFullName(user.getFullName());
        entity.setUserName(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(generateHashedPassword(user.getPassword()));
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);

        var dto = repository.save(entity);
        return new AccountCredentialsDto(dto.getUsername(),  dto.getEmail(), dto.getPassword(), dto.getFullName());
    }

    private String generateHashedPassword(String password) {

        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
                "", 8, 185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder.encode(password);
    }

    public User getEmailOrUsername(String email, String username){
        var userEmail = repository.findByEmail(email);

        if (userEmail == null) {
            var userUsername = repository.findByUsername(username);
            if (userUsername == null) {
                throw new UsernameNotFoundException("Username or email not found!");
            }
            return userUsername;
        }
        return userEmail;
    }
}
